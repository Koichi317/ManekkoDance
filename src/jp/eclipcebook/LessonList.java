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
		setTitle("Lesson�I�����");
		List<String> lessonList = new ArrayList<String>(); // List�̍쐬���ǉ�
		lessonList.add("Lesson�P ��{����");
		lessonList.add("Lesson�Q ���p����");
		lessonList.add("Lesson�R ���s(loop)");
		lessonList.add("Lesson�S ���s�Ɖ��p����(2�dloop)");
		arrayadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
				lessonList);
		this.setListAdapter(arrayadapter);
	}

	/************ List���I�����ꂽ���̓��� **************/
	protected void onListItemClick(ListView l, View v, int position, long id) {
		/*
		 * SharedPreferences sp = PreferenceManager
		 * .getDefaultSharedPreferences(getApplication());
		 */

		switch (position) {
		case 0:
			Intent intent0 = new Intent(this, jp.eclipcebook.PartnerActivity.class);
			// intent0.putExtra("lesson0", sp.getString("lesson0", null));
			intent0.putExtra("lesson", getText(R.string.answer_lesson1)); // ����{�̓���
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
