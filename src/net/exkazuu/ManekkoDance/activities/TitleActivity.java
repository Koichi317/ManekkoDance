package net.exkazuu.ManekkoDance.activities;

import jp.eclipcebook.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class TitleActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // タイトルバー非表示
		setContentView(R.layout.title_screen);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			appearButtons();
			break;
		}
		return false;
	}

	public void appearButtons() {
		Button helpButton = (Button) findViewById(R.id.help_button);
		helpButton.setVisibility(View.VISIBLE);
		Button startButton = (Button) findViewById(R.id.start_button);
		startButton.setVisibility(View.VISIBLE);
	}

	public void viewHelp(View view) {
		Intent intent = new Intent(this, net.exkazuu.ManekkoDance.Help.class);
		this.startActivity(intent);
	}

	public void startLesson(View view) {
		Intent intent = new Intent(this,
				net.exkazuu.ManekkoDance.LessonList.class);
		this.startActivity(intent);
	}

}
