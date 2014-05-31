package net.exkazuu.ManekkoDance;

public class Lessons {
//	private static String[] _answers = { // easy
//	"左腕を上げる右腕を上げる\n左腕を下げる\n右腕を下げる",
//			"左腕を上げる\n左腕を下げる\n右腕を上げる\n右腕を下げる\n左腕を上げる\n左腕を下げる\n右腕を上げる\n右腕を下げる",
//			"くりかえし2\n左腕を上げる\n左腕を下げる\n右腕を上げる\n右腕を下げる\nここまで",
//			"くりかえし2\nくりかえし2\n左腕を上げる\n左腕を下げる\nここまで\n右腕を上げる\n右腕を下げる\nここまで",
//			"右腕を上げる\nもしも黄色\n右腕を下げる\nもしくは\n左腕を上げる\nもしおわり",
//			"くりかえし3\nもしも黄色\n右腕を上げる\n右腕を下げる\nもしくは\n左腕を上げる\n左腕を下げる\nもしおわり\nここまで", };
	private static String[] _answers = { // hard
			"左腕を上げる右腕を上げる\n左腕を下げる\n右腕を下げる",
			"左腕を上げる\n左腕を下げる\n右腕を上げる\n右腕を下げる\n左腕を上げる\n左腕を下げる\n右腕を上げる\n右腕を下げる\n左腕を上げる\n左腕を下げる\n右腕を上げる\n右腕を下げる\n左腕を上げる\n左腕を下げる\n右腕を上げる\n右腕を下げる\n左腕を上げる\n左腕を下げる\n右腕を上げる\n右腕を下げる",
			"くりかえし5\n左腕を上げる\n左腕を下げる\n右腕を上げる\n右腕を下げる\nここまで",
			"くりかえし2\nくりかえし2\n左腕を上げる\n左腕を下げる\nここまで\n右腕を上げる\n右腕を下げる\nここまで",
			"右腕を上げる\nもしも黄色\n右腕を下げる\nもしくは\n左腕を上げる\nもしおわり",
			"くりかえし3\nもしも黄色\n右腕を上げる\n右腕を下げる\nもしくは\n左腕を上げる\n左腕を下げる\nもしおわり\nここまで", };

//	private static int[] _limitations = { 10, 10, 8, 10, 10, 10, }; // easy

	private static int[] _limitations = { 10, 22, 20, 10, 10, 10, }; // hard

	public static String getAnswer(int lessonNumber) {
		if (!(1 <= lessonNumber && lessonNumber <= _answers.length)) {
			throw new IndexOutOfBoundsException("lessonNumber (" + lessonNumber
					+ ") should be in 1-" + _answers.length);
		}
		return _answers[lessonNumber - 1];
	}

	public static int getLimitation(int lessonNumber) {
		return _limitations[lessonNumber - 1];
	}
}
