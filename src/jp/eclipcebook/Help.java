package jp.eclipcebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Help extends Activity {

	private String lesson;
	private String text_data;
	private String message;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		
		Intent intent = getIntent();
		lesson = intent.getStringExtra("lesson");
		message = intent.getStringExtra("message");
		text_data = intent.getStringExtra("text_data");

		
		EditText helpText = (EditText)this.findViewById(R.id.helpText);
		helpText.setText("(1) 基本動作\n命令は一行ずつ順番に実行される。\n\n(2) 応用動作\n一行に複数の命令を書くと、同時実行される。\n\n(3) ジャンプ\nジャンプする前は基本姿勢でなければいけない。\n\n(4) 繰り返し\n---\nloop 回数\n動作\nここまで\n---\nのように使い、処理を回数分繰り返す。\n動作とは\"左腕を上げる\"などの命令を指す。");
	}
	
	public void changeScreen(View view) {
		Intent intent = new Intent(this, jp.eclipcebook.MainActivity.class);
		intent.putExtra("lesson", lesson);
		intent.putExtra("message", message);
		intent.putExtra("text_data", text_data);
		this.startActivity(intent);
	}
}
