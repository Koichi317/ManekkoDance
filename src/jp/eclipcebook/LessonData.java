package jp.eclipcebook;

public class LessonData {
	public static String getLessonData(int number) {
		
		String answer = null;
		
		switch(number) {
		case 1:
			answer = "���r���グ��\n���r��������\n�E�r���グ��\n�E�r��������";
			break;
		case 2:
			answer = "���r���グ�� �E�r���グ��\n���r��������\n�E�r��������\n���r���グ��\n�E�r���グ��\n���r�������� �E�r��������\n�W�����v����";
			break;
		case 3:
			answer = "���r���グ��\n���r��������\n�E�r���グ��\n�E�r��������";
			break;
		case 4:
			answer = "���r���グ��\n���r��������\n�E�r���グ��\n�E�r��������";
			break;
		default:
			answer = "null";
			break;
		}
		return answer;
	}
}
