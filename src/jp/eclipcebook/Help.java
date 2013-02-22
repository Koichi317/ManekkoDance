package jp.eclipcebook;

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
	private String page;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);

		Intent intent = getIntent();
		lesson = intent.getStringExtra("lesson");
		message = intent.getStringExtra("message");
		text_data = intent.getStringExtra("text_data");
		pageNumber = 1;
		page = " / 8";
		ImageView helpImage = (ImageView) findViewById(R.id.helpImage);
		helpImage.setImageResource(R.drawable.tutorial1);
		EditText pageText = (EditText)findViewById(R.id.page);
		pageText.setText("1 / 8");
	}

	public void next(View view) {
		if (pageNumber == 8)
			pageNumber = 0;
		pageNumber++;

		ImageView helpImage = (ImageView) findViewById(R.id.helpImage);
		EditText pageText = (EditText)findViewById(R.id.page);
		pageText.setText(String.valueOf(pageNumber) + page);

		switch (pageNumber) {
		case 1:
			helpImage.setImageResource(R.drawable.tutorial1);
			break;
		case 2:
			helpImage.setImageResource(R.drawable.tutorial2);
			break;
		case 3:
			helpImage.setImageResource(R.drawable.tutorial3);
			break;
		case 4:
			helpImage.setImageResource(R.drawable.tutorial4);
			break;
		case 5:
			helpImage.setImageResource(R.drawable.tutorial5);
			break;
		case 6:
			helpImage.setImageResource(R.drawable.helptext1);
			break;
		case 7:
			helpImage.setImageResource(R.drawable.helptext2);
			break;
		case 8:
			helpImage.setImageResource(R.drawable.helptext3);
			break;
		}

	}

	public void prev(View view) {
		if (pageNumber == 1)
			pageNumber = 9;
		pageNumber--;

		ImageView helpImage = (ImageView) findViewById(R.id.helpImage);
		EditText pageText = (EditText)findViewById(R.id.page);
		pageText.setText(String.valueOf(pageNumber) + page);

		switch (pageNumber) {
		case 1:
			helpImage.setImageResource(R.drawable.tutorial1);
			break;
		case 2:
			helpImage.setImageResource(R.drawable.tutorial2);
			break;
		case 3:
			helpImage.setImageResource(R.drawable.tutorial3);
			break;
		case 4:
			helpImage.setImageResource(R.drawable.tutorial4);
			break;
		case 5:
			helpImage.setImageResource(R.drawable.tutorial5);
			break;
		case 6:
			helpImage.setImageResource(R.drawable.helptext1);
			break;
		case 7:
			helpImage.setImageResource(R.drawable.helptext2);
			break;
		case 8:
			helpImage.setImageResource(R.drawable.helptext3);
			break;
		}

	}

	
	public void changeScreen(View view) {
		Intent intent = new Intent();
		intent.putExtra("lesson", lesson);
		intent.putExtra("message", message);
		intent.putExtra("text_data", text_data);
		finish();
	}
}
