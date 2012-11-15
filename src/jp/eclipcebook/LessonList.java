package jp.eclipcebook;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LessonList extends ListActivity {
	private ArrayAdapter<String> arrayadapter = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Lesson�I�����");
		List<String> lessonList = new ArrayList<String>(); // List�̍쐬���ǉ�
		lessonList.add("Lesson�P ��{����");
		lessonList.add("Lesson�Q ���p����");
		lessonList.add("Lesson�R ���s");
		lessonList.add("Lesson�S loop���s");
		arrayadapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, lessonList);
		this.setListAdapter(arrayadapter);
	}

	/************ List���I�����ꂽ���̓��� **************/
	protected void onListItemClick(ListView l, View v, int position, long id) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(getApplication());

		switch (position) {
		case 0:
			// Lesson�ɉ���������{�̓��������[�h�������iToDo�j
			Intent intent0 = new Intent(this, jp.eclipcebook.MainActivity.class);
			intent0.putExtra("lesson0", "test"/*sp.getString("lesson0", null)*/);
			this.startActivity(intent0);
			break;
		case 1:
			Intent intent1 = new Intent(this, jp.eclipcebook.MainActivity.class);
			this.startActivity(intent1);
			break;
		case 2:
			Intent intent2 = new Intent(this, jp.eclipcebook.MainActivity.class);
			this.startActivity(intent2);
			break;
		case 3:
			Intent intent3 = new Intent(this, jp.eclipcebook.MainActivity.class);
			this.startActivity(intent3);
			break;
		default:
			break;
		}
		super.onListItemClick(l, v, position, id);
	}

}
