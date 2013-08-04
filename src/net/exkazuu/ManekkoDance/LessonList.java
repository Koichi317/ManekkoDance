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
		setTitle("Lesson‘I‘ğ‰æ–Ê");
		List<String> lessonList = new ArrayList<String>(); // List‚Ìì¬•’Ç‰Á
		lessonList.add("Lesson1");
		lessonList.add("Lesson2");
		lessonList.add("Lesson3");

		// lessonList.add("Lesson‚P Šî–{“®ì1");
		// lessonList.add("Lesson‚Q Šî–{“®ì2");
		// lessonList.add("Lesson‚R Šî–{“®ì•œK");
		// lessonList.add("Lesson‚S ‰—p“®ì1");
		// lessonList.add("Lesson‚T ‰—p“®ì2");
		// lessonList.add("Lesson‚U ‰—p“®ì3");
		// lessonList.add("Lesson‚V ŒJ‚è•Ô‚µ1");
		// lessonList.add("Lesson‚W ŒJ‚è•Ô‚µ2");
		// lessonList.add("Lesson‚X ŒJ‚è•Ô‚µ3");
		// lessonList.add("Lesson‚P‚O ŒJ‚è•Ô‚µ4");
		// lessonList.add("Lesson‚P‚P ‚¨‚Ü‚¯1");
		// lessonList.add("Lesson‚P‚Q ‚¨‚Ü‚¯2");
		// lessonList.add("Lesson‚P‚R ‚¨‚Ü‚¯3");
		arrayadapter = new MySpecialAdapter(this,
				android.R.layout.simple_list_item_1, lessonList);
		this.setListAdapter(arrayadapter);
	}

	/************ List‚ª‘I‘ğ‚³‚ê‚½‚Ì“®ì **************/
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
		
		String answer = LessonData.getLessonData(position + 1);
		intent.putExtra("lesson", answer); // ‚¨è–{‚Ì“š‚¦
		intent.putExtra("message", "" + (position + 1));
		this.startActivity(intent);
		// TODO: Deal with exceptions

		super.onListItemClick(l, v, position, id);
	}
}
