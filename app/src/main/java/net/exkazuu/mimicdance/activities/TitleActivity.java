package net.exkazuu.mimicdance.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.activeandroid.query.Select;

import net.exkazuu.mimicdance.R;
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
        //freeButton.setVisibility(View.VISIBLE);
        freeButton.setVisibility(View.GONE);

        copyDatabaseToClipboard();
    }

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
        startLessonListActivity(true);
    }

    public void freePlay(View view) {
    }
}
