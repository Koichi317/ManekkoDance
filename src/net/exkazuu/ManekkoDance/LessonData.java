package net.exkazuu.ManekkoDance;

public class LessonData {
	public static String getLessonData(int number) {

		String answer = null;

		switch (number) {
		case 1:
			answer = "���r���グ��\n���r��������\n�E�r���グ��\n�E�r��������";
			break;
		case 2:
			answer = "�������グ��\n������������\n�E�����グ��\n�E����������";
			break;
		case 3:
			answer = "�������グ��\n������������\n�E�����グ��\n�E����������\n���r���グ��\n���r��������\n�W�����v����";
			break;
		case 4:
			answer = "���r���グ�� �E�r���グ��\n���r��������\n�E�r��������\n���r���グ��\n�E�r���グ��\n���r�������� �E�r��������";
			break;
		case 5:
			answer = "���r���グ�� �E�����グ��\n���r�������� �E����������\n�E�r���グ�� �������グ��\n�E�r�������� ������������";
			break;
		case 6:
			answer = "���r���グ�� �E�r���グ��\n���r�������� �E�r��������\n���r���グ�� �������グ��\n �E�r���グ��\n ������������\n���r�������� �E�r��������\n�W�����v����";
			break;
		case 7:
			answer = "loop3\n�������グ��\n������������\n�E�����グ��\n�E����������\n�����܂�";
			break;
		case 8:
			answer = "loop3\n�������グ��\n������������\n�E�����グ��\n�E����������\n�����܂�";
			break;
		case 9:
			answer = "loop3\n�������グ��\n������������\n�E�����グ��\n�E����������\n�����܂�\n loop2\n���r���グ�� �E�r���グ��\n ���r�������� �E�r��������\n�����܂�";
			break;
		case 10:
			answer = "loop3\n loop2\n ���r���グ�� �E�r���グ��\n���r�������� �E�r��������\n�����܂�\n�W�����v����\n�����܂�";
			break;
		case 11:
			answer = "loop2\n���r���グ��\n���r��������\n�����܂�";
			break;
		case 12:
			answer = "loop2\n���r���グ��\n���r��������\n�����܂�\nloop2\n�E�r���グ��\n�E�r��������\n�����܂�";
			break;
		case 13:
			answer = "loop2\nloop2\n���r���グ��\n���r��������\n�����܂�\n�E�r���グ��\n�E�r��������\n�����܂�";
			break;
		default:
			answer = "null";
			break;
		}
		return answer;
	}
}
