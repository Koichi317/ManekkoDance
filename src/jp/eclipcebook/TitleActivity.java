package jp.eclipcebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

public class TitleActivity extends Activity{
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //タイトルバー非表示
		setContentView(R.layout.title_screen);
	}
	
	public void doActionTitle(View view) {
		RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.titleButton);
		relativeLayout.setVisibility(View.VISIBLE);
	}
	
	public void doActionFromTheBiginning(View view) {
		Intent intent = new Intent(this, jp.eclipcebook.LessonList.class);
		this.startActivity(intent);
	}
	
	public void doActionContinue(View view) {
		Intent intent = new Intent(this, jp.eclipcebook.LessonList.class);
		this.startActivity(intent);
	}
	
}
