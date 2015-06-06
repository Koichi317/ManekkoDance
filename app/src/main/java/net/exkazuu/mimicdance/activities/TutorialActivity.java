package net.exkazuu.mimicdance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ViewFlipper;

import net.exkazuu.mimicdance.R;

public class TutorialActivity extends BaseActivity {
    private float lastTouchX;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // タイトルバー非表示
        setContentView(R.layout.tutorial);

        final ViewFlipper viewFlipper = (ViewFlipper) this.findViewById(R.id.flipper);

        viewFlipper.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // A
                        lastTouchX = event.getX();
                        break;
                    case MotionEvent.ACTION_UP: // B
                        float currentX = event.getX();
                        if (lastTouchX < currentX) {
                            viewFlipper.showPrevious();
                        }
                        if (lastTouchX > currentX) {
                            viewFlipper.showNext();
                        }
                        break;
                }
                return true; // C
            }
        });
    }

    public void startTitleActivity(View view) {
        Intent intent = new Intent(this, TitleActivity.class);
        startActivity(intent);
    }
}
