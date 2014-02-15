package net.exkazuu.ManekkoDance.activities;

import net.exkazuu.ManekkoDance.Help;
import net.exkazuu.ManekkoDance.LessonList;
import jp.eclipcebook.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class TitleActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // タイトルバー非表示
		setContentView(R.layout.title_screen);
	}

	public void doActionTitle(View view) {
		RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.titleButton);
		relativeLayout.setVisibility(View.VISIBLE);
	}

	public void doActionFromTheBeginning(View view) {
		// MediaPlayer bgm = MediaPlayer.create(getApplicationContext(),
		// R.raw.titlecall);
		// bgm.start();
		Intent intent = new Intent(this, net.exkazuu.ManekkoDance.Help.class);
		this.startActivity(intent);
	}

	public void doActionContinue(View view) {
		// MediaPlayer bgm = MediaPlayer.create(getApplicationContext(),
		// R.raw.titlecall);
		// bgm.start();
		Intent intent = new Intent(this, net.exkazuu.ManekkoDance.LessonList.class);
		this.startActivity(intent);
	}

	protected void onDestroy() {
		super.onDestroy();
		cleanupView(findViewById(R.id.titleRoot));
		System.gc();
	}

	public static final void cleanupView(View view) {
		if (view instanceof ImageButton) {
			ImageButton ib = (ImageButton) view;
			ib.setImageDrawable(null);
		} else if (view instanceof ImageView) {
			ImageView iv = (ImageView) view;
			iv.setImageDrawable(null);
			// } else if(view instanceof(XXX)) {
			// 他にもDrawableを使用する対象があればここで中身をnullに
		}
		view.setBackgroundDrawable(null);
		if (view instanceof ViewGroup) {
			ViewGroup vg = (ViewGroup) view;
			int size = vg.getChildCount();
			for (int i = 0; i < size; i++) {
				cleanupView(vg.getChildAt(i));
			}
		}
	}

}
