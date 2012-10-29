package jp.eclipcebook;

import java.util.ArrayList;
import java.util.List;

import android.widget.Toast;

public class AnswerCheck {

	static List<String> Answer1 = new ArrayList<String>();
	public static boolean judge;
	
	public AnswerCheck() { //ダンスの答え（後程訂正すべき）
		Answer1.clear();
		Answer1.add("右腕を上げる");
		Answer1.add("右腕を下げる");
		Answer1.add("右腕を上げる");
		Answer1.add("右腕を下げる");
		Answer1.add("右腕を上げる");
		Answer1.add("右腕を下げる");
		Answer1.add("ジャンプする");
		Answer1.add("\n");
	}

	public void compare(List<String> expandedCommands) {
		// TODO Auto-generated method stub
	int minor = Math.max(Answer1.size(), expandedCommands.size());
		for(int i = 0; i < minor; i++) {
			if(expandedCommands.get(i).indexOf(Answer1.get(i)) != -1) {  //展開された配列と答え合わせ
				judge = true;
			} else {
				judge = false;
				break;
			}
		}
	}
	
	public String show() {
		// TODO Auto-generated method stub
		if(judge == true) {
			return "正解です！";
		}else {
			return "不正解です・・・";
		}
	}

}
