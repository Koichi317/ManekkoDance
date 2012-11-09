package jp.eclipcebook;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LessonList extends ListActivity {
	private ArrayAdapter<String> arrayadapter = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Lesson選択画面");
		List<String> lessonList = new ArrayList<String>(); //Listの作成＆追加
		lessonList.add("Lesson１ 基本動作");
		lessonList.add("Lesson２ 応用動作");
		lessonList.add("Lesson３ 歩行");
		lessonList.add("Lesson４ loop歩行");
		arrayadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,lessonList);
		this.setListAdapter(arrayadapter);		
	}

/************ Listが選択された時の動作 **************/
	protected void onListItemClick(ListView l, View v, int position, long id) {
		switch(position) {
		case 0:
			//Lessonに応じたお手本の動きをロードしたい（ToDo）
			Intent intent0 = new Intent(this, jp.eclipcebook.MainActivity.class);
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
