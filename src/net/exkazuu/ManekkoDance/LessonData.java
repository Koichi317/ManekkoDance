package net.exkazuu.ManekkoDance;

public class LessonData {
	private static String[] _answers = {
			"���r���グ��\n���r��������\n�E�r���グ��\n�E�r��������",
			"loop3\n���r���グ��\n���r��������\n�E�r���グ��\n�E�r��������\n�����܂�\n�W�����v����",
			"loop2\n���r���グ��\n���r��������\n�E�����グ��\n�E����������\n�����܂�\n�W�����v����\nloop2\n�������グ��\n������������\n�E�r���グ��\n�E�r��������\n�����܂�",
			"���r���グ�� �E�r���グ��\n���r��������\n�E�r��������\n���r���グ��\n�E�r���グ��\n���r�������� �E�r��������",
			"���r���グ�� �E�����グ��\n���r�������� �E����������\n�E�r���グ�� �������グ��\n�E�r�������� ������������",
			"���r���グ�� �E�r���グ��\n���r�������� �E�r��������\n���r���グ�� �������グ��\n �E�r���グ��\n ������������\n���r�������� �E�r��������\n�W�����v����",
			"loop3\n�������グ��\n������������\n�E�����グ��\n�E����������\n�����܂�",
			"loop3\n�������グ��\n������������\n�E�����グ��\n�E����������\n�����܂�",
			"loop3\n�������グ��\n������������\n�E�����グ��\n�E����������\n�����܂�\n loop2\n���r���グ�� �E�r���グ��\n ���r�������� �E�r��������\n�����܂�",
			"loop3\n loop2\n ���r���グ�� �E�r���グ��\n���r�������� �E�r��������\n�����܂�\n�W�����v����\n�����܂�",
			"loop2\n���r���グ��\n���r��������\n�����܂�",
			"loop2\n���r���グ��\n���r��������\n�����܂�\nloop2\n�E�r���グ��\n�E�r��������\n�����܂�",
			"loop2\nloop2\n���r���グ��\n���r��������\n�����܂�\n�E�r���グ��\n�E�r��������\n�����܂�", };

	public static String getLessonData(int lessonNumber) {
		if (!(1 <= lessonNumber && lessonNumber <= _answers.length)) {
			throw new IndexOutOfBoundsException("lessonNumber (" + lessonNumber
					+ ") should be in 1-" + _answers.length);
		}
		return _answers[lessonNumber - 1];
	}
}
