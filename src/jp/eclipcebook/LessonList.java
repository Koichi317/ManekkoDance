package jp.eclipcebook;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

public class LessonList extends ListActivity {
	private ArrayAdapter<String> arrayadapter = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Lesson選択画面");
		List<String> lessonList = new ArrayList<String>(); // Listの作成＆追加
		lessonList.add("Lesson１ 基本動作");
		lessonList.add("Lesson２ 応用動作");
		lessonList.add("Lesson３ 歩行(loop)");
		lessonList.add("Lesson４ 歩行と応用動作(2重loop)");
		arrayadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
				lessonList);
		this.setListAdapter(arrayadapter);
	}

	/************ Listが選択された時の動作 **************/
	protected void onListItemClick(ListView l, View v, int position, long id) {
		/*
		 * SharedPreferences sp = PreferenceManager
		 * .getDefaultSharedPreferences(getApplication());
		 */

		switch (position) {
		case 0:
			Intent intent0 = new Intent(this, jp.eclipcebook.PartnerActivity.class);
			// intent0.putExtra("lesson0", sp.getString("lesson0", null));
			intent0.putExtra("lesson", getText(R.string.answer_lesson1)); // お手本の答え
			intent0.putExtra("message", "1");
			this.startActivity(intent0);
			break;
		case 1:
			Intent intent1 = new Intent(this, jp.eclipcebook.PartnerActivity.class);
			intent1.putExtra("lesson", getText(R.string.answer_lesson2));
			intent1.putExtra("message", "2");
			this.startActivity(intent1);
			break;
		case 2:
			Intent intent2 = new Intent(this, jp.eclipcebook.PartnerActivity.class);
			intent2.putExtra("lesson", getText(R.string.answer_lesson3));
			intent2.putExtra("message", "3");
			this.startActivity(intent2);
			break;
		case 3:
			Intent intent3 = new Intent(this, jp.eclipcebook.PartnerActivity.class);
			intent3.putExtra("lesson", getText(R.string.answer_lesson4));
			intent3.putExtra("message", "4");
			this.startActivity(intent3);
			break;
		default:
			break;
		}
		super.onListItemClick(l, v, position, id);
	}
}
