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
		helpText.setText("(1) ��{����\n���߂͈�s�����ԂɎ��s�����B\n\n(2) ���p����\n��s�ɕ����̖��߂������ƁA�������s�����B\n\n(3) �W�����v\n�W�����v����O�͊�{�p���łȂ���΂����Ȃ��B\n\n(4) �J��Ԃ�\n---\nloop ��\n����\n�����܂�\n---\n�̂悤�Ɏg���A�������񐔕��J��Ԃ��B\n����Ƃ�\"���r���グ��\"�Ȃǂ̖��߂��w���B");
	}
	
	public void changeScreen(View view) {
		Intent intent = new Intent(this, jp.eclipcebook.MainActivity.class);
		intent.putExtra("lesson", lesson);
		intent.putExtra("message", message);
		intent.putExtra("text_data", text_data);
		this.startActivity(intent);
	}
}
