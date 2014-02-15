package net.exkazuu.ManekkoDance;

public class LessonData {
	private static String[] _answers = {
			"左腕を上げる\n左腕を下げる\n右腕を上げる\n右腕を下げる",
			"loop3\n左腕を上げる\n左腕を下げる\n右腕を上げる\n右腕を下げる\nここまで\nジャンプする",
			"loop2\n左腕を上げる\n左腕を下げる\n右足を上げる\n右足を下げる\nここまで\nジャンプする\nloop2\n左足を上げる\n左足を下げる\n右腕を上げる\n右腕を下げる\nここまで",
			"左腕を上げる 右腕を上げる\n左腕を下げる\n右腕を下げる\n左腕を上げる\n右腕を上げる\n左腕を下げる 右腕を下げる",
			"左腕を上げる 右足を上げる\n左腕を下げる 右足を下げる\n右腕を上げる 左足を上げる\n右腕を下げる 左足を下げる",
			"左腕を上げる 右腕を上げる\n左腕を下げる 右腕を下げる\n左腕を上げる 左足を上げる\n 右腕を上げる\n 左足を下げる\n左腕を下げる 右腕を下げる\nジャンプする",
			"loop3\n左足を上げる\n左足を下げる\n右足を上げる\n右足を下げる\nここまで",
			"loop3\n左足を上げる\n左足を下げる\n右足を上げる\n右足を下げる\nここまで",
			"loop3\n左足を上げる\n左足を下げる\n右足を上げる\n右足を下げる\nここまで\n loop2\n左腕を上げる 右腕を上げる\n 左腕を下げる 右腕を下げる\nここまで",
			"loop3\n loop2\n 左腕を上げる 右腕を上げる\n左腕を下げる 右腕を下げる\nここまで\nジャンプする\nここまで",
			"loop2\n左腕を上げる\n左腕を下げる\nここまで",
			"loop2\n左腕を上げる\n左腕を下げる\nここまで\nloop2\n右腕を上げる\n右腕を下げる\nここまで",
			"loop2\nloop2\n左腕を上げる\n左腕を下げる\nここまで\n右腕を上げる\n右腕を下げる\nここまで", };

	public static String getLessonData(int lessonNumber) {
		if (!(1 <= lessonNumber && lessonNumber <= _answers.length)) {
			throw new IndexOutOfBoundsException("lessonNumber (" + lessonNumber
					+ ") should be in 1-" + _answers.length);
		}
		return _answers[lessonNumber - 1];
	}
}
