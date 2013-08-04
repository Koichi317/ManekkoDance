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
		setTitle("Lesson�I�����");
		List<String> lessonList = new ArrayList<String>(); // List�̍쐬���ǉ�
		lessonList.add("Lesson1");
		lessonList.add("Lesson2");
		lessonList.add("Lesson3");

		// lessonList.add("Lesson�P ��{����1");
		// lessonList.add("Lesson�Q ��{����2");
		// lessonList.add("Lesson�R ��{���앜�K");
		// lessonList.add("Lesson�S ���p����1");
		// lessonList.add("Lesson�T ���p����2");
		// lessonList.add("Lesson�U ���p����3");
		// lessonList.add("Lesson�V �J��Ԃ�1");
		// lessonList.add("Lesson�W �J��Ԃ�2");
		// lessonList.add("Lesson�X �J��Ԃ�3");
		// lessonList.add("Lesson�P�O �J��Ԃ�4");
		// lessonList.add("Lesson�P�P ���܂�1");
		// lessonList.add("Lesson�P�Q ���܂�2");
		// lessonList.add("Lesson�P�R ���܂�3");
		arrayadapter = new MySpecialAdapter(this,
				android.R.layout.simple_list_item_1, lessonList);
		this.setListAdapter(arrayadapter);
	}

	/************ List���I�����ꂽ���̓��� **************/
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
		intent.putExtra("lesson", answer); // ����{�̓���
		intent.putExtra("message", "" + (position + 1));
		this.startActivity(intent);
		// TODO: Deal with exceptions

		super.onListItemClick(l, v, position, id);
	}
}
