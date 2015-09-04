package net.exkazuu.mimicdance.activities;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

public class UsbStateChangeReceiver extends BroadcastReceiver {
    private static final String TAG = "UsbStateChangeReceiver";

    // 本アプリを認識するためのインテントアクション名
    private static final String ACTION_USB_PERMISSION
        = "net.exkazuu.mimicdance.action.USB_PERMISSION";
    private static UsbStateChangeReceiver instance;

    private static final byte[] buffer = new byte[2];

    private static UsbManager usbManager;
    private static ParcelFileDescriptor fileDescriptor;
    private static FileOutputStream outputStream;// 出力用ストリーム
    private static UsbAccessory accessory;
    private static PendingIntent permissionIntent;
    private static boolean permissionRequestPending;

    private UsbStateChangeReceiver() {
    }

    private static UsbStateChangeReceiver getInstance() {
        if (instance == null) {
            instance = new UsbStateChangeReceiver();
        }
        return instance;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (ACTION_USB_PERMISSION.equals(action)) {
            // USBホストシールドがUSBコネクタに接続した場合
            synchronized (this) {
                // Intent からアクセサリを取得
                UsbAccessory accessory = intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);

                // 接続許可ダイアログで OK=true, Cancel=false のどちらを押したか
                if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                    openAccessory(accessory);
                } else {
                    Log.d(TAG, "Permission denied for accessory: " + accessory);
                }

                permissionRequestPending = false;
            }
        } else if (UsbManager.ACTION_USB_ACCESSORY_DETACHED.equals(action)) {
            // USBホストシールドがUSBコネクタから外された場合
            UsbAccessory accessory = intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);
            // 接続中のUSBアクセサリか？
            if (accessory != null && accessory.equals(UsbStateChangeReceiver.accessory)) {
                // 接続中のUSBアクセサリなら接続を閉じる
                closeAccessory();
            }
        }
    }

    public static void register(Activity activity) {
        // UsbManager のインスタンスを取得
        usbManager = (UsbManager) activity.getSystemService(Context.USB_SERVICE);

        // パーミッション・インテントの作成（自分自身のアプリから発行）
        permissionIntent = PendingIntent.getBroadcast(activity, 0, new Intent(ACTION_USB_PERMISSION), 0);

        // ブロードキャストレシーバで受信するインテントを登録
        IntentFilter filter = new IntentFilter();
        // USBアクセサリが接続／切断されたときのインテント・フィルター
        filter.addAction(ACTION_USB_PERMISSION);
        // USBアクセサリが切断された（取り外された）ときのインテント・フィルター
        filter.addAction(UsbManager.ACTION_USB_ACCESSORY_DETACHED);
        activity.registerReceiver(getInstance(), filter);
    }

    public static void unregister(Activity activity) {
        closeAccessory();
        activity.unregisterReceiver(getInstance());
    }

    public static void resume() {
        // 未初期化の場合は何もしない
        if (usbManager == null) {
            return;
        }

        // 既に通信が開始していたら何もしない
        if (outputStream != null) {
            return;
        }

        // 接続されているUSBアクセサリの確認
        UsbAccessory[] accessories = usbManager.getAccessoryList();
        UsbAccessory accessory = (accessories == null ? null : accessories[0]);
        if (accessory == null) {
            Log.d(TAG, "accessory is null");
            return;
        }

        // USBアクセサリにアクセスする権限があるかチェック
        if (usbManager.hasPermission(accessory)) {
            openAccessory(accessory);
        } else {
            synchronized (getInstance()) {
                if (!permissionRequestPending) {
                    // パーミッションを依頼
                    usbManager.requestPermission(accessory, permissionIntent);
                    permissionRequestPending = true;
                }
            }
        }
    }

    public static boolean isOpened() {
        return outputStream != null;
    }

    /**
     * USBアクセサリへの出力ストリームを開く。
     *
     * @param accessory
     */
    private static void openAccessory(UsbAccessory accessory) {
        UsbStateChangeReceiver.accessory = accessory;

        // アクセサリにアクセスするためのファイルディスクリプタを取得
        fileDescriptor = usbManager.openAccessory(accessory);

        if (fileDescriptor != null) {
            FileDescriptor fd = fileDescriptor.getFileDescriptor();

            // 入出力用のストリームを確保
            outputStream = new FileOutputStream(fd);

            Log.d(TAG, "accessory opened");
        } else {
            Log.d(TAG, "accessory open fail");
        }
    }

    /**
     * USBアクセサリへの出力ストリームを閉じる。
     */
    private static void closeAccessory() {

        try {
            if (fileDescriptor != null) {
                fileDescriptor.close();
            }
        } catch (IOException e) {
        } finally {
            accessory = null;
            fileDescriptor = null;
        }
    }

    /**
     * 出力：Androidアプリ（USBアクセサリ）-> Arduino（USBホスト）
     *
     * @param value
     * @param i
     */
    public static void sendCommand(byte value, int i) {
        // 2バイトのプロトコルデータ
        buffer[i] = value;
        if (outputStream != null) {
            try {
                // 出力ストリームにbuffer[]配列データを書き込む(7)
                outputStream.write(buffer);
            } catch (IOException e) {
                Log.e(TAG, "sendCommand:write failed", e);
            }
        }
    }
}
