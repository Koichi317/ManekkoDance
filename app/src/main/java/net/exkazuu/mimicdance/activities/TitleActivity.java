package net.exkazuu.mimicdance.activities;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import net.exkazuu.mimicdance.R;

public class TitleActivity extends BaseActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // タイトルバー非表示
        setContentView(R.layout.titleActivity);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                showButtons();
                break;
        }
        return false;
    }

    public void showButtons() {
        Button helpButton = (Button) findViewById(R.id.help_button);
        helpButton.setVisibility(View.VISIBLE);
        Button startButton = (Button) findViewById(R.id.start_button);
        startButton.setVisibility(View.VISIBLE);
        Button freeButton = (Button) findViewById(R.id.free_button);
        //freeButton.setVisibility(View.VISIBLE);
        freeButton.setVisibility(View.GONE);
    }

    public void startHelpActivity(View view) {
        startHelpActivity();
    }

    public void startLessonListActivity(View view) {
        startLessonListActivity();
    }

    public void freePlay(View view) {
    }
}
