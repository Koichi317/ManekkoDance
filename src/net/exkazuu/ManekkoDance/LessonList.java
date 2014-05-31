package net.exkazuu.ManekkoDance;

import java.util.ArrayList;
import java.util.List;

import jp.eclipcebook.R;
import android.app.ListActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class LessonList extends ListActivity {
	private MySpecialAdapter arrayadapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Lesson選択画面");
		List<String> lessonList = new ArrayList<String>(); // Listの作成＆追加
		lessonList.add("Lesson1");
		lessonList.add("Lesson2");
		lessonList.add("Lesson3");
		lessonList.add("Lesson4");
		lessonList.add("Lesson5");
		lessonList.add("Lesson6");

		// lessonList.add("Lesson１ 基本動作1");
		// lessonList.add("Lesson２ 基本動作2");
		// lessonList.add("Lesson３ 基本動作復習");
		// lessonList.add("Lesson４ 応用動作1");
		// lessonList.add("Lesson５ 応用動作2");
		// lessonList.add("Lesson６ 応用動作3");
		// lessonList.add("Lesson７ 繰り返し1");
		// lessonList.add("Lesson８ 繰り返し2");
		// lessonList.add("Lesson９ 繰り返し3");
		// lessonList.add("Lesson１０ 繰り返し4");
		// lessonList.add("Lesson１１ おまけ1");
		// lessonList.add("Lesson１２ おまけ2");
		// lessonList.add("Lesson１３ おまけ3");
		arrayadapter = new MySpecialAdapter(this,
				android.R.layout.simple_list_item_1, lessonList);
		this.setListAdapter(arrayadapter);
	}

	/************ Listが選択された時の動作 **************/
	protected void onListItemClick(ListView l, View v, int position, long id) {
		/*
		 * SharedPreferences sp = PreferenceManager
		 * .getDefaultSharedPreferences(getApplication());
		 */
		MediaPlayer bgm = MediaPlayer
				.create(getApplicationContext(), R.raw.get);
		bgm.start();

		Intent intent = new Intent(this,
				net.exkazuu.ManekkoDance.activities.PartnerActivity.class);
		
		String answer = Lessons.getAnswer(position + 1);
		intent.putExtra("lesson", answer); // お手本の答え
		intent.putExtra("message", "" + (position + 1));
		this.startActivity(intent);
		// TODO: Deal with exceptions

		super.onListItemClick(l, v, position, id);
	}
}
