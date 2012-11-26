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
		setTitle("Lesson‘I‘ğ‰æ–Ê");
		List<String> lessonList = new ArrayList<String>(); // List‚Ìì¬•’Ç‰Á
		lessonList.add("Lesson‚P Šî–{“®ì");
		lessonList.add("Lesson‚Q ‰—p“®ì");
		lessonList.add("Lesson‚R •às(loop)");
		lessonList.add("Lesson‚S •às‚Æ‰—p“®ì(2dloop)");
		arrayadapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, lessonList);
		this.setListAdapter(arrayadapter);
	}

	/************ List‚ª‘I‘ğ‚³‚ê‚½‚Ì“®ì **************/
	protected void onListItemClick(ListView l, View v, int position, long id) {
/*		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(getApplication()); */

		switch (position) {
		case 0:
			Intent intent0 = new Intent(this, jp.eclipcebook.PartnerActivity.class);
			//intent0.putExtra("lesson0", sp.getString("lesson0", null));
			intent0.putExtra("lesson",
					"¶˜r‚ğã‚°‚é\n¶˜r‚ğ‰º‚°‚é\n‰E˜r‚ğã‚°‚é\n‰E˜r‚ğ‰º‚°‚é"); // ‚¨è–{‚Ì“š‚¦
			intent0.putExtra("message", "lesson1");
			this.startActivity(intent0);
			break;
		case 1:
			Intent intent1 = new Intent(this, jp.eclipcebook.PartnerActivity.class);
			intent1.putExtra("lesson",
					"¶˜r‚ğã‚°‚é ‰E˜r‚ğã‚°‚é\n¶˜r‚ğ‰º‚°‚é\n‰E˜r‚ğ‰º‚°‚é\n¶˜r‚ğã‚°‚é\n‰E˜r‚ğã‚°‚é\n¶˜r‚ğ‰º‚°‚é ‰E˜r‚ğ‰º‚°‚é\nƒWƒƒƒ“ƒv‚·‚é");
			intent1.putExtra("message", "lesson2");
			this.startActivity(intent1);
			break;
		case 2:
			Intent intent2 = new Intent(this, jp.eclipcebook.PartnerActivity.class);
			intent2.putExtra("lesson",
					"loop3\n¶‘«‚ğã‚°‚é\n¶‘«‚ğ‰º‚°‚é\n‰E‘«‚ğã‚°‚é\n‰E‘«‚ğ‰º‚°‚é\n‚±‚±‚Ü‚Å\nƒWƒƒƒ“ƒv‚·‚é");
			intent2.putExtra("message", "lesson3");
			this.startActivity(intent2);
			break;
		case 3:
			Intent intent3 = new Intent(this, jp.eclipcebook.PartnerActivity.class);
			intent3.putExtra(
					"lesson",
					"loop3\nloop2\n¶‘«‚ğã‚°‚é\n¶‘«‚ğ‰º‚°‚é\n‰E‘«‚ğã‚°‚é\n‰E‘«‚ğ‰º‚°‚é\n‚±‚±‚Ü‚Å\n¶˜r‚ğã‚°‚é ‰E˜r‚ğã‚°‚é\n¶˜r‚ğ‰º‚°‚é ‰E˜r‚ğ‰º‚°‚é\n‚±‚±‚Ü‚Å\nƒWƒƒƒ“ƒv‚·‚é");
			intent3.putExtra("message", "lesson4");
			this.startActivity(intent3);
			break;
		default:
			break;
		}
		super.onListItemClick(l, v, position, id);
	}

}
