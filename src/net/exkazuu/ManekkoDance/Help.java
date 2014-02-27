package net.exkazuu.ManekkoDance;

import jp.eclipcebook.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class Help extends Activity {

	private String lesson;
	private String text_data;
	private String message;
	private int pageNumber;
	private final String page = " / 8";

	int[] helpImageResources = { R.drawable.tutorial1, R.drawable.tutorial2,
			R.drawable.tutorial3, R.drawable.tutorial4, R.drawable.tutorial5,
			R.drawable.helptext1, R.drawable.helptext2, R.drawable.helptext3 };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);

		Intent intent = getIntent();
		lesson = intent.getStringExtra("lesson");
		message = intent.getStringExtra("message");
		text_data = intent.getStringExtra("text_data");
		pageNumber = 1;
		ImageView helpImage = (ImageView) findViewById(R.id.helpImage);
		helpImage.setImageResource(R.drawable.tutorial1);
		EditText pageText = (EditText) findViewById(R.id.page);
		pageText.setText("1 / 8");
	}

	public void next(View view) {
		if (pageNumber == 8)
			pageNumber = 0;
		pageNumber++;
		setHelpImage();
	}

	public void prev(View view) {
		if (pageNumber == 1)
			pageNumber = 9;
		pageNumber--;
		setHelpImage();
	}

	public void changeScreen(View view) {
		Intent intent = new Intent();
		intent.putExtra("lesson", lesson);
		intent.putExtra("message", message);
		intent.putExtra("text_data", text_data);
		finish();
	}

	public void setHelpImage() {
		ImageView helpImage = (ImageView) findViewById(R.id.helpImage);
		EditText pageText = (EditText) findViewById(R.id.page);
		pageText.setText(String.valueOf(pageNumber) + page);
		helpImage.setImageResource(helpImageResources[pageNumber - 1]);
	}
}