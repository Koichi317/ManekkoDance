package jp.eclipcebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ViewFlipper;

public class Tutorial extends Activity {
	private ViewFlipper viewflipper;
	private float lastTouchX;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial);

        
        viewflipper = (ViewFlipper)this.findViewById(R.id.flipper);
        
        this.viewflipper.setOnTouchListener(new OnTouchListener() {
            
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: //A
                        lastTouchX = event.getX();
                        break;
                    case MotionEvent.ACTION_UP: //B
                        float currentX = event.getX();
                        if (lastTouchX < currentX) {
                            viewflipper.showPrevious();
                        }
                        if (lastTouchX > currentX) {
                            viewflipper.showNext();
                        }
                        break;
                }
                return true; //C
            }
        });
	}
	
	public void changeTitleScreen(View view) {
		Intent intent = new Intent(this, jp.eclipcebook.TitleActivity.class);
		this.startActivity(intent);
	}
}
