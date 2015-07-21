package net.exkazuu.mimicdance.activities;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.activeandroid.query.Select;

import net.exkazuu.mimicdance.R;
import net.exkazuu.mimicdance.models.LessonClear;

import java.util.List;

public class TitleActivity extends BaseActivity {

//    private static IntentFilter plugIntentFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
//    private static BroadcastReceiver plugStateChangeReceiver = null;
//
//    boolean isPlugged = false;
//    AlertDialog.Builder alert;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // タイトルバー非表示
        setContentView(R.layout.title);

        Button helpButton = (Button) findViewById(R.id.help_button);
        helpButton.setVisibility(View.VISIBLE);
        Button startButton = (Button) findViewById(R.id.start_button);
        startButton.setVisibility(View.VISIBLE);
        Button freeButton = (Button) findViewById(R.id.free_button);
        //freeButton.setVisibility(View.VISIBLE);
        freeButton.setVisibility(View.GONE);

        copyDatabaseToClipboard();


//        plugStateChangeReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                // plug状態を取得
//                if (intent.getIntExtra("state", 0) > 0) {
//                    isPlugged = true;
//                } else isPlugged = false;
//
//                // plug状態でメッセージを変更。
//                if (isPlugged) {
//                    // ヘッドセットが挿された
////                    alert.setTitle("Headset inserted.");
////                    alert.show();
//                } else {
//                    // ヘッドセットが抜かれた
////                    alert.setTitle("Headset はずす.");
////                    alert.show();
//                }
//            }
//        };


    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        registerReceiver(plugStateChangeReceiver, plugIntentFilter);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        unregisterReceiver(plugStateChangeReceiver);
//    }

    private void copyDatabaseToClipboard() {
        StringBuilder builder = new StringBuilder();
        List<LessonClear> lessonClears = new Select().from(LessonClear.class).orderBy("Created_at").execute();
        for (LessonClear clear : lessonClears) {
            builder.append(clear.created_at + ", " + clear.lessonNumber + ", " + clear.milliseconds / 1000 + ", " + clear.moveCount);
            builder.append('\n');
        }

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("simple text", builder.toString());
        clipboard.setPrimaryClip(clip);
    }


    public void startHelpActivity(View view) {
        startHelpActivity(false);
    }

    public void startLessonListActivity(View view) {
//        alert = new AlertDialog.Builder(this);
//        if (isPlugged) alert.setTitle("刺さってる");
//        else alert.setTitle("刺さってない");
//        alert.show();

        startLessonListActivity(true);
    }

    public void freePlay(View view) {
    }
}
