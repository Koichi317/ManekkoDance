package jp.eclipcebook;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
		setTitle("Lesson選択画面");
		List<String> lessonList = new ArrayList<String>(); // Listの作成＆追加
		lessonList.add("Lesson１ 基本動作");
		lessonList.add("Lesson２ 応用動作");
		lessonList.add("Lesson３ 歩行(loop)");
		lessonList.add("Lesson４ 歩行と応用動作(2重loop)");
		arrayadapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, lessonList);
		this.setListAdapter(arrayadapter);
	}

	/************ Listが選択された時の動作 **************/
	protected void onListItemClick(ListView l, View v, int position, long id) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(getApplication());

		switch (position) {
		case 0:
			// Lessonに応じたお手本の動きをロードしたい（ToDo）
			Intent intent0 = new Intent(this, jp.eclipcebook.PartnerActivity.class);
			intent0.putExtra("lesson0", sp.getString("lesson0", null));
			intent0.putExtra("lesson",
					"左腕を上げる\n左腕を下げる\n右腕を上げる\n右腕を下げる\nジャンプする"); // お手本の答え
			intent0.putExtra("message", "lesson1");
			this.startActivity(intent0);
			break;
		case 1:
			Intent intent1 = new Intent(this, jp.eclipcebook.PartnerActivity.class);
			intent1.putExtra("lesson",
					"左腕を上げる 右腕を上げる\n左腕を下げる\n右腕を下げる\n左腕を上げる\n右腕を上げる\n左腕を下げる 右腕を下げる\nジャンプする");
			intent1.putExtra("message", "lesson2");
			this.startActivity(intent1);
			break;
		case 2:
			Intent intent2 = new Intent(this, jp.eclipcebook.PartnerActivity.class);
			intent2.putExtra("lesson",
					"loop3\n左足を上げる\n左足を下げる\n右足を上げる\n右足を下げる\nここまで\nジャンプする");
			intent2.putExtra("message", "lesson3");
			this.startActivity(intent2);
			break;
		case 3:
			Intent intent3 = new Intent(this, jp.eclipcebook.PartnerActivity.class);
			intent3.putExtra(
					"lesson",
					"loop3\nloop2\n左足を上げる\n左足を下げる\n右足を上げる\n右足を下げる\nここまで\n左腕を上げる 右腕を上げる\n左腕を下げる 右腕を下げる\nここまで\nジャンプする");
			intent3.putExtra("message", "lesson4");
			this.startActivity(intent3);
			break;
		default:
			break;
		}
		super.onListItemClick(l, v, position, id);
	}

}
