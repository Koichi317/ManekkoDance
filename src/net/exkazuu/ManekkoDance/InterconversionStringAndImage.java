package net.exkazuu.ManekkoDance;

import java.util.Map.Entry;
import java.util.TreeMap;

import android.content.Context;

public class InterconversionStringAndImage {

	// public InterconversionStringAndImage() {
	//
	// }

	String convertStringToImage(String text1, ImageInEdit mImageEdit,
			Context context) {
		String[] commands = new String[] { "���r���グ��", "���r��������"/* .. */};

		for (String line : commands /* fix it */) {
			TreeMap<Integer, String> map = new TreeMap<Integer, String>();
			for (String command : commands) {
				int i = -1;
				while ((i = line.indexOf(command, i + 1)) >= 0) {
					map.put(i, command);
				}
			}

			for (Entry<Integer, String> indexAndStr : map.entrySet()) {
				String command = indexAndStr.getValue();

				if (command.equals("���r���グ��")) {

				} else if (command.equals("���r��������")) {

				} else if (command.equals("�E�r���グ��")) {

				}
				// ...
			}

		}

		// ��������G�����ւ̕ϊ�
		// text1��tab1�̃e�L�X�g�f�[�^���擾���A�G�����ɕϊ������f�[�^��Ԃ�
		// text1 = text1.replaceAll("���r���グ��",
		// mImageEdit.insertResourceImage(context,
		// R.drawable.icon_left_hand_up));
		// text1 = text1.replaceAll("���r��������",
		// mImageEdit.insertResourceImage(context,
		// R.drawable.icon_left_hand_down));
		// text1 = text1.replaceAll("�E�r���グ��",
		// mImageEdit.insertResourceImage(context(),
		// R.drawable.icon_right_hand_up));
		// text1 = text1.replaceAll("�E�r��������",
		// mImageEdit.insertResourceImage(context(),
		// R.drawable.icon_right_hand_down));
		// text1 = text1.replaceAll("�������グ��",
		// mImageEdit.insertResourceImage(context(),
		// R.drawable.icon_left_foot_up));
		// text1 = text1.replaceAll("������������",
		// mImageEdit.insertResourceImage(context(),
		// R.drawable.icon_left_foot_down));
		// text1 = text1.replaceAll("�E�����グ��",
		// mImageEdit.insertResourceImage(context(),
		// R.drawable.icon_right_foot_up));
		// text1 = text1.replaceAll("�E����������",
		// mImageEdit.insertResourceImage(context(),
		// R.drawable.icon_right_foot_down));
		// text1 = text1.replaceAll("�W�����v����",
		// mImageEdit.insertResourceImage(context(), R.drawable.icon_jump));
		// text1 = text1.replaceAll("loop",
		// mImageEdit.insertResourceImage(context(), R.drawable.icon_loop));
		// text1 = text1.replaceAll("�����܂�",
		// mImageEdit.insertResourceImage(context(), R.drawable.icon_kokomade));
		return text1;
	}

	String convertImageToString(String text2) {
		// �G�������當���ւ̕ϊ�
		// text2��tab2�̃e�L�X�g�f�[�^���擾���A�����ɕϊ������f�[�^��Ԃ�
		return text2;
	}

}
