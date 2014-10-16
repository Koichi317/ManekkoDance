package net.exkazuu.ManekkoDance.activities;

import jp.eclipcebook.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
//import android.text.Html;
//import android.text.Spanned;
//import android.util.Log;
import android.view.Menu;
import android.view.View;
//import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

//import android.widget.TextView;

public class DragAndDropActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 背景たち
		ImageView[][] cells = new ImageView[3][9];

		cells[0][0] = (ImageView) findViewById(R.id.image0_0);
		cells[1][0] = (ImageView) findViewById(R.id.image1_0);
		cells[2][0] = (ImageView) findViewById(R.id.image2_0);
		cells[0][1] = (ImageView) findViewById(R.id.image0_1);
		cells[1][1] = (ImageView) findViewById(R.id.image1_1);
		cells[2][1] = (ImageView) findViewById(R.id.image2_1);
		cells[0][2] = (ImageView) findViewById(R.id.image0_2);
		cells[1][2] = (ImageView) findViewById(R.id.image1_2);
		cells[2][2] = (ImageView) findViewById(R.id.image2_2);
		cells[0][3] = (ImageView) findViewById(R.id.image0_3);
		cells[1][3] = (ImageView) findViewById(R.id.image1_3);
		cells[2][3] = (ImageView) findViewById(R.id.image2_3);
		cells[0][4] = (ImageView) findViewById(R.id.image0_4);
		cells[1][4] = (ImageView) findViewById(R.id.image1_4);
		cells[2][4] = (ImageView) findViewById(R.id.image2_4);
		cells[0][5] = (ImageView) findViewById(R.id.image0_5);
		cells[1][5] = (ImageView) findViewById(R.id.image1_5);
		cells[2][5] = (ImageView) findViewById(R.id.image2_5);
		cells[0][6] = (ImageView) findViewById(R.id.image0_6);
		cells[1][6] = (ImageView) findViewById(R.id.image1_6);
		cells[2][6] = (ImageView) findViewById(R.id.image2_6);
		cells[0][7] = (ImageView) findViewById(R.id.image0_7);
		cells[1][7] = (ImageView) findViewById(R.id.image1_7);
		cells[2][7] = (ImageView) findViewById(R.id.image2_7);
		cells[0][8] = (ImageView) findViewById(R.id.image0_8);
		cells[1][8] = (ImageView) findViewById(R.id.image1_8);
		cells[2][8] = (ImageView) findViewById(R.id.image2_8);

		// 右側のテキストたち
		String[][] program = new String[3][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 3; j++) {
				program[j][i] = null;
			}
		}

		TextView text = (TextView) findViewById(R.id.tvCount1);
		text.setText(program[0][0] + program[1][0] + program[2][0] + "\n"
				+ program[0][1] + program[1][1] + program[2][1] + "\n"
				+ program[0][2] + program[1][2] + program[2][2] + "\n"
				+ program[0][3] + program[1][3] + program[2][3] + "\n"
				+ program[0][4] + program[1][4] + program[2][4] + "\n"
				+ program[0][5] + program[1][5] + program[2][5] + "\n"
				+ program[0][6] + program[1][6] + program[2][6] + "\n"
				+ program[0][7] + program[1][7] + program[2][7] + "\n"
				+ program[0][8] + program[1][8] + program[2][8]);

		// ドラッグ対象Viewとイベント処理クラスを紐付ける
		// アイコンたち
		ImageView dragView1 = (ImageView) findViewById(R.id.imageView1);
		ImageView dragView2 = (ImageView) findViewById(R.id.imageView2);
		ImageView dragView3 = (ImageView) findViewById(R.id.imageView3);
		ImageView dragView4 = (ImageView) findViewById(R.id.imageView4);
		ImageView dragView5 = (ImageView) findViewById(R.id.imageView5);
		ImageView dragView6 = (ImageView) findViewById(R.id.imageView6);
		ImageView dragView7 = (ImageView) findViewById(R.id.imageView7);
		ImageView dragView8 = (ImageView) findViewById(R.id.imageView8);
		ImageView dragView9 = (ImageView) findViewById(R.id.imageView9);
		ImageView dragView10 = (ImageView) findViewById(R.id.imageView10);
		ImageView dragView11 = (ImageView) findViewById(R.id.imageView11);
		ImageView dragGomi = (ImageView) findViewById(R.id.imageGomi);

		DragViewListener listener1 = new DragViewListener(dragView1, cells,
				program, text);
		dragView1.setOnTouchListener(listener1);
		DragViewListener listener2 = new DragViewListener(dragView2, cells,
				program, text);
		dragView2.setOnTouchListener(listener2);
		DragViewListener listener3 = new DragViewListener(dragView3, cells,
				program, text);
		dragView3.setOnTouchListener(listener3);
		DragViewListener listener4 = new DragViewListener(dragView4, cells,
				program, text);
		dragView4.setOnTouchListener(listener4);
		DragViewListener listener5 = new DragViewListener(dragView5, cells,
				program, text);
		dragView5.setOnTouchListener(listener5);
		DragViewListener listener6 = new DragViewListener(dragView6, cells,
				program, text);
		dragView6.setOnTouchListener(listener6);
		DragViewListener listener7 = new DragViewListener(dragView7, cells,
				program, text);
		dragView7.setOnTouchListener(listener7);
		DragViewListener listener8 = new DragViewListener(dragView8, cells,
				program, text);
		dragView8.setOnTouchListener(listener8);
		DragViewListener listener9 = new DragViewListener(dragView9, cells,
				program, text);
		dragView9.setOnTouchListener(listener9);
		DragViewListener listener10 = new DragViewListener(dragView10, cells,
				program, text);
		dragView10.setOnTouchListener(listener10);
		DragViewListener listener11 = new DragViewListener(dragView11, cells,
				program, text);
		dragView11.setOnTouchListener(listener11);
		DragViewListener listenerGomi = new DragViewListener(dragGomi, cells,
				program, text);
		dragGomi.setOnTouchListener(listenerGomi);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	String html = "";

	/*
	 * public void onButtonClick1(View v) { ImageGetterImpl imageGetter = new
	 * ImageGetterImpl( getApplicationContext()); TextView tv = (TextView)
	 * findViewById(R.id.textview1); html +=
	 * getResources().getString(R.string.imgstring1); Spanned words =
	 * Html.fromHtml(html, imageGetter, null); tv.setText(words); }
	 */

	/*
	 * public void onButtonClick2(View v) { ImageGetterImpl imageGetter = new
	 * ImageGetterImpl( getApplicationContext()); TextView tv = (TextView)
	 * findViewById(R.id.textview1); html +=
	 * getResources().getString(R.string.imgstring2); Spanned words =
	 * Html.fromHtml(html, imageGetter, null); tv.setText(words); }
	 */
}
