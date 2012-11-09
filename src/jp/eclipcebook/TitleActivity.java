package jp.eclipcebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class TitleActivity extends Activity{
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //タイトルバー非表示
		setContentView(R.layout.title_screen);
	}
	
	public void doActionFromTheBiginning(View view) {
		
	}
	
	public void doActionContinue(View view) {
		Intent intent = new Intent(this, jp.eclipcebook.MainActivity.class);
		this.startActivity(intent);
	}
}
