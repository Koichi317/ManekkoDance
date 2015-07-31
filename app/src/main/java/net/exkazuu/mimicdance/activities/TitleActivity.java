package net.exkazuu.mimicdance.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.activeandroid.query.Select;

import net.exkazuu.mimicdance.R;
import net.exkazuu.mimicdance.controller.PwmMotorController;
import net.exkazuu.mimicdance.models.LessonClear;

import java.util.List;

public class TitleActivity extends BaseActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        copyDatabaseToClipboard();
    }

    private void copyDatabaseToClipboard() {
        StringBuilder builder = new StringBuilder();
        List<LessonClear> lessonClears = new Select().from(LessonClear.class).orderBy("Created_at").execute();
        String number = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        for (LessonClear clear : lessonClears) {
            builder.append(number + ", " + clear.created_at + ", " + clear.lessonNumber + ", " + clear.milliseconds / 1000 + ", " + clear.moveCount);
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
        startLessonListActivity(true);
    }

    public void startPreQuestionnaireActivity(View view) {
        startPreQuestionnaireActivity(false);
    }

    public void startPostQuestionnaireActivity(View view) {
        startPostQuestionnaireActivity(false);
    }


    public void freePlay(View view) {
        controller.play();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        controller.setPulseMilliseconds(2.5, 0.5);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        controller.release();
    }

    private PwmMotorController controller = new PwmMotorController(50000, 50, 1.5, 1.5);
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
}
