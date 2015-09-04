package net.exkazuu.mimicdance.activities;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.activeandroid.query.Select;

import net.exkazuu.mimicdance.R;
import net.exkazuu.mimicdance.models.LessonClear;
import net.exkazuu.mimicdance.models.PostQuestionnaireResult;
import net.exkazuu.mimicdance.models.PreQuestionnaireResult;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import im.delight.android.ddp.MeteorSingleton;

public class TitleActivity extends BaseActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!MeteorSingleton.isCreated()) {
            MeteorSingleton.createInstance(this, "ws://mimic-dance-server.herokuapp.com/websocket");
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE); // タイトルバー非表示
        setContentView(R.layout.title);

        Button helpButton = (Button) findViewById(R.id.help_button);
        helpButton.setVisibility(View.VISIBLE);
        Button startButton = (Button) findViewById(R.id.start_button);
        startButton.setVisibility(View.VISIBLE);
        Button freeButton = (Button) findViewById(R.id.free_button);
        freeButton.setVisibility(View.GONE);
        Button preButton = (Button) findViewById(R.id.pre_ques_button);
        preButton.setVisibility(View.VISIBLE);
        Button postButton = (Button) findViewById(R.id.post_ques_button);
        postButton.setVisibility(View.VISIBLE);

        uploadData();

        initializeUsbManager();
    }

    public void startHelpActivity(View view) {
        uploadData();
        startHelpActivity(false);
    }

    public void startLessonListActivity(View view) {
        uploadData();
        startLessonListActivity(true);
    }

    public void startPreQuestionnaireActivity(View view) {
        uploadData();
        startPreQuestionnaireActivity(false);
    }

    public void startPostQuestionnaireActivity(View view) {
        uploadData();
        startPostQuestionnaireActivity(false);
    }

    public void freePlay(View view) {
    }

    public void uploadData() {
        MeteorSingleton.getInstance().reconnect();
        List<LessonClear> lessonClears = new Select().from(LessonClear.class).where("Sent = ?", false).orderBy("Created_at").execute();
        List<PreQuestionnaireResult> preQuestionnaireResults = new Select().from(PreQuestionnaireResult.class).where("Sent = ?", false).orderBy("Created_at").execute();
        List<PostQuestionnaireResult> postQuestionnaireResults = new Select().from(PostQuestionnaireResult.class).where("Sent = ?", false).orderBy("Created_at").execute();
        Log.d("upload", "lessonClears: " + lessonClears.size());
        Log.d("upload", "preQuestionnaireResults: " + preQuestionnaireResults.size());
        Log.d("upload", "postQuestionnaireResults: " + postQuestionnaireResults.size());
        try {
            if (!MeteorSingleton.getInstance().isConnected()) {
                return;
            }
            for (LessonClear item : lessonClears) {
                Map<String, Object> values = new HashMap<>();
                values.put("created_at", item.created_at);
                values.put("examineeId", item.examineeId);
                values.put("lessonNumber", item.lessonNumber);
                values.put("seconds", item.milliseconds / 1000);
                values.put("moveCount", item.moveCount);
                MeteorSingleton.getInstance().insert("PlayLogs", values);
                item.sent = true;
                item.save();
            }

            String androidId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
            for (PreQuestionnaireResult item : preQuestionnaireResults) {
                Map<String, Object> values = new HashMap<>();
                values.put("created_at", item.created_at);
                values.put("examineeId", item.examineeId);
                values.put("androidId", androidId);
                values.put("type", "豪華版");
                values.put("sex", item.sex);
                values.put("age", item.age);
                values.put("knowledgeOfProgramming", item.knowledgeOfProgramming);
                values.put("knowledgeOfMimicDance", item.knowledgeOfMimicDance);
                values.put("desireToLearn", item.desireToLearn);
                values.put("fun", item.fun);
                values.put("feasibility", item.feasibility);
                values.put("usefulness", item.usefulness);
                MeteorSingleton.getInstance().insert("PreQuestionnaireResults", values);
                item.sent = true;
                item.save();
            }

            for (PostQuestionnaireResult item : postQuestionnaireResults) {
                Map<String, Object> values = new HashMap<>();
                values.put("created_at", item.created_at);
                values.put("examineeId", item.examineeId);
                values.put("gladness", item.gladness);
                values.put("vexation", item.vexation);
                values.put("desireToPlay", item.desireToPlay);
                values.put("additionalPlayTime", item.additionalPlayTime);
                values.put("desireToLearn", item.desireToLearn);
                values.put("fun", item.fun);
                values.put("feasibility", item.feasibility);
                values.put("usefulness", item.usefulness);
                values.put("opinion", item.opinion);
                MeteorSingleton.getInstance().insert("PostQuestionnaireResults", values);
                item.sent = true;
                item.save();
            }
            Log.d("upload", "uploaded");
        } catch (Exception e) {
        }
    }

    //    private PwmMotorController controller = new PwmMotorController(50000, 50, 1.5, 1.5);
//
//    @Override
//    protected void onPause() {
//        controller.release();
//        super.onPause();
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//            case MotionEvent.ACTION_MOVE:
//                WindowManager wm = getWindowManager();
//                Display disp = wm.getDefaultDisplay();
//                Point size = new Point();
//                disp.getSize(size);
//                double leftPulseMilliseconds = 0.5 + ((double) event.getX() / size.x) * 2;
//                double rightPulseMilliseconds = 0.5 + ((double) event.getY() / size.y) * 2;
//                controller.setLeftPulseMilliseconds(leftPulseMilliseconds);
//                controller.setRightPulseMilliseconds(rightPulseMilliseconds);
//                short[] buffer = controller.generatePwmData();
//                int count = 0;
//                for (short v : buffer) {
//                    if (v == Short.MAX_VALUE) {
//                        count++;
//                    }
//                }
//                ((TextView) findViewById(R.id.txtPulseLength)).setText("Left: " + leftPulseMilliseconds);
//                ((TextView) findViewById(R.id.txtFrequency)).setText("Right: " + rightPulseMilliseconds);
//                ((TextView) findViewById(R.id.txtCheck)).setText("Check: " + count);
//                controller.play();
//                break;
//            case MotionEvent.ACTION_UP:
//                controller.stop();
//                break;
//        }
//        return super.onTouchEvent(event);
//    }

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

    //USB接続状態を監視するブロードキャストレシーバ
    private final BroadcastReceiver usbReceiver = new BroadcastReceiver() {

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
                if (accessory != null && accessory.equals(TitleActivity.this.accessory)) {
                    // 接続中のUSBアクセサリなら接続を閉じる
                    closeAccessory();
                }
            }
        }
    };

    private void initializeUsbManager() {
        // UsbManager のインスタンスを取得
        usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

        // パーミッション・インテントの作成（自分自身のアプリから発行）
        permissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);

        // ブロードキャストレシーバで受信するインテントを登録
        IntentFilter filter = new IntentFilter();
        //USBアクセサリが接続／切断されたときのインテント・フィルター
        filter.addAction(ACTION_USB_PERMISSION);
        //USBアクセサリが切断された（取り外された）ときのインテント・フィルター
        filter.addAction(UsbManager.ACTION_USB_ACCESSORY_DETACHED);
        registerReceiver(usbReceiver, filter);
    }

    //アプリ起動時の処理 OnResume()メソッド（Activityライフサイクル）
    @Override
    public void onResume() {
        super.onResume();

        //既に通信しているか
        if (outputStream != null) {
            return;
        }

        // 接続されているUSBアクセサリの確認
        UsbAccessory[] accessories = usbManager.getAccessoryList();
        UsbAccessory accessory = (accessories == null ? null : accessories[0]);
        if (accessory != null) {
            // USBアクセサリ にアクセスする権限があるかチェック
            if (usbManager.hasPermission(accessory)) {
                // 接続許可されているならば、アプリを起動
                openAccessory(accessory);
            } else {
                // 接続許可されていないのならば、パーミッションインテント発行
                synchronized (usbReceiver) {
                    if (!permissionRequestPending) {
                        // パーミッションを依頼
                        usbManager.requestPermission(accessory, permissionIntent);
                        permissionRequestPending = true;
                    }
                }
            }
        } else {
            Log.d(TAG, "accessory is null");
        }
    }

    //他のActivityが開始される時の処理 OnPause()メソッド（Activityライフサイクル）
    @Override
    public void onPause() {
        super.onPause();
        closeAccessory();
    }

    //アプリ終了時の処理 OnDestroy()メソッド（Activityライフサイクル）
    @Override
    public void onDestroy() {
        unregisterReceiver(usbReceiver);
        super.onDestroy();
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
            AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(this);
            alertDialogBuilder1.setTitle("USB accessory");
            alertDialogBuilder1.setMessage("Connected.");
            alertDialogBuilder1.show();
        } else {
            Log.d(TAG, "accessory open fail");
        }
    }

    // USBアクセサリ終了処理
    private void closeAccessory() {
        try {
            if (fileDescriptor != null) {
                fileDescriptor.close();
                AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(this);
                alertDialogBuilder2.setTitle("USB accessory");
                alertDialogBuilder2.setMessage("Disconnected.");
                alertDialogBuilder2.show();

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
