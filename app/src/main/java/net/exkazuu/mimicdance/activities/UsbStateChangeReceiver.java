package net.exkazuu.mimicdance.activities;

/**
 * Created by t-yokoi on 2015/09/04.
 */
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class UsbStateChangeReceiver extends BroadcastReceiver{
    private static final String TAG = "UsbStateChangeReceiver";
    private static final String ACTION_USB_PERMISSION
        = "net.exkazuu.mimicdance.activities.action.USB_PERMISSION";
    private PendingIntent mPermissionIntent;
    private boolean mPermissionRequestPending;

    private UsbManager mUsbManager;
    private UsbAccessory mAccessory;

    ParcelFileDescriptor mFileDescriptor;

    FileOutputStream mOutputStream;// 出力用ストリーム


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        //
        if(ACTION_USB_PERMISSION.equals(action)) {
            synchronized (this) {
                //intentからアクセサリを習得
                UsbAccessory accessory = (UsbAccessory) intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);
                //接続許可ダイアログ
                if(intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED,false)) {
                    openAccessory(accessory);
                } else {
                    Log.d(TAG, "permission denied for accessory " + accessory);
                }
                mPermissionRequestPending = false;
            }
            //USBホストシールドがUSBコネクタから外された場合
        } else if (UsbManager.ACTION_USB_ACCESSORY_DETACHED.equals(action)) {
            UsbAccessory accessory = (UsbAccessory) intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);
            //接続中のUSBアクセサリか？
            if(accessory != null && accessory.equals(mAccessory)) {
                //接続中のUSBアクセサリならば接続を閉じる
                closeAccessory();
            }
        }
    }

    //USBアクセサリ開始処理
    private void openAccessory(UsbAccessory accessory) {
        // アクセサリにアクセスするためのファイルディスクリプタを取得
        mFileDescriptor = mUsbManager.openAccessory(accessory);

        if (mFileDescriptor != null) {
            mAccessory = accessory;
            FileDescriptor fd = mFileDescriptor.getFileDescriptor();

            // 入出力用のストリームを確保（今回は出力のみ）(5)
            mOutputStream = new FileOutputStream(fd);

            Log.d(TAG, "accessory opened");
        } else {
            Log.d(TAG, "accessory open fail");
        }
    }

    // USBアクセサリ終了処理
    private void closeAccessory() {

        try {
            if (mFileDescriptor != null) {
                mFileDescriptor.close();
            }
        } catch (IOException e) {
        } finally {
            mFileDescriptor = null;
            mAccessory = null;
        }
    }

}
