package jp.eclipcebook;

import java.util.ArrayList;
import java.util.List;

import android.widget.Toast;

public class AnswerCheck {

	static List<String> Answer1 = new ArrayList<String>();
	public static boolean judge;
	
	public AnswerCheck() { //�_���X�̓����i����������ׂ��j
		Answer1.clear();
		Answer1.add("�E�r���グ��");
		Answer1.add("�E�r��������");
		Answer1.add("�E�r���グ��");
		Answer1.add("�E�r��������");
		Answer1.add("�E�r���グ��");
		Answer1.add("�E�r��������");
		Answer1.add("�W�����v����");
		Answer1.add("\n");
	}

	public void compare(List<String> expandedCommands) {
		// TODO Auto-generated method stub
	int minor = Math.max(Answer1.size(), expandedCommands.size());
		for(int i = 0; i < minor; i++) {
			if(expandedCommands.get(i).indexOf(Answer1.get(i)) != -1) {  //�W�J���ꂽ�z��Ɠ������킹
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
			return "�����ł��I";
		}else {
			return "�s�����ł��E�E�E";
		}
	}

}
