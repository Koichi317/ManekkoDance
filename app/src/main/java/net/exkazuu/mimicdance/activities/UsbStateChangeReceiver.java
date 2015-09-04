package net.exkazuu.mimicdance.activities;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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

    private PendingIntent permissionIntent;
    private boolean permissionRequestPending;

    private UsbManager usbManager;
    private UsbAccessory accessory;

    private ParcelFileDescriptor fileDescriptor;

    private FileOutputStream outputStream;// 出力用ストリーム

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        //ACTION_USB_PERMISSIONの場合
        if (ACTION_USB_PERMISSION.equals(action)) {
            synchronized (this) {
                // Intent からアクセサリを取得
                UsbAccessory accessory = intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);
                // 接続許可ダイアログで OK=true, Cancel=false のどちらを押したか
                if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                    openAccessory(accessory);
                } else {
                    Log.d(TAG, "permission denied for accessory " + accessory);
                }
                permissionRequestPending = false;
            }
            //USBホストシールドがUSBコネクタから外された場合
        } else if (UsbManager.ACTION_USB_ACCESSORY_DETACHED.equals(action)) {
            UsbAccessory accessory = intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);
            // 接続中のUSBアクセサリか？
            if (accessory != null && accessory.equals(this.accessory)) {
                // 接続中のUSBアクセサリなら接続を閉じる
                closeAccessory();
            }
        }
    }


    private void resume() {
        // 既に通信しているか
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

        // USBアクセサリ にアクセスする権限があるかチェック
        if (usbManager.hasPermission(accessory)) {
            // 接続許可されているならば、アプリを起動
            openAccessory(accessory);
        } else {
            // 接続許可されていないのならば、パーミッションインテント発行
            synchronized (this) {
                if (!permissionRequestPending) {
                    // パーミッションを依頼
                    usbManager.requestPermission(accessory, permissionIntent);
                    permissionRequestPending = true;
                }
            }
        }
    }

    //USBアクセサリ開始処理
    private void openAccessory(UsbAccessory accessory) {
        // アクセサリにアクセスするためのファイルディスクリプタを取得
        fileDescriptor = usbManager.openAccessory(accessory);

        if (fileDescriptor != null) {
            this.accessory = accessory;
            FileDescriptor fd = fileDescriptor.getFileDescriptor();

            // 入出力用のストリームを確保（今回は出力のみ）(5)
            outputStream = new FileOutputStream(fd);

            Log.d(TAG, "accessory opened");
        } else {
            Log.d(TAG, "accessory open fail");
        }
    }

    // USBアクセサリ終了処理
    private void closeAccessory() {
        try {
            if (fileDescriptor != null) {
                fileDescriptor.close();
            }
        } catch (IOException e) {
        } finally {
            fileDescriptor = null;
            accessory = null;
        }
    }

    private byte[] buffer = new byte[2];

    // 出力：Androidアプリ（USBアクセサリ）-> Arduino（USBホスト）(6)
    public void sendCommand(byte value, int i) {
        // 2バイトのプロトコルデータ
        buffer[i] = value;
        if (outputStream != null) {
            try {

                //出力ストリームにbuffer[]配列データを書き込む(7)
                outputStream.write(buffer);

            } catch (IOException e) {
                Log.e(TAG, "sendCommand:write failed", e);
            }
        }
    }
}
