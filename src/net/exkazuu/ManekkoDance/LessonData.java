package net.exkazuu.ManekkoDance;

public class LessonData {
	public static String getLessonData(int number) {

		String answer = null;

		switch (number) {
		case 1:
			answer = "左腕を上げる\n左腕を下げる\n右腕を上げる\n右腕を下げる";
			break;
		case 2:
			answer = "左足を上げる\n左足を下げる\n右足を上げる\n右足を下げる";
			break;
		case 3:
			answer = "左足を上げる\n左足を下げる\n右足を上げる\n右足を下げる\n左腕を上げる\n左腕を下げる\nジャンプする";
			break;
		case 4:
			answer = "左腕を上げる 右腕を上げる\n左腕を下げる\n右腕を下げる\n左腕を上げる\n右腕を上げる\n左腕を下げる 右腕を下げる";
			break;
		case 5:
			answer = "左腕を上げる 右足を上げる\n左腕を下げる 右足を下げる\n右腕を上げる 左足を上げる\n右腕を下げる 左足を下げる";
			break;
		case 6:
			answer = "左腕を上げる 右腕を上げる\n左腕を下げる 右腕を下げる\n左腕を上げる 左足を上げる\n 右腕を上げる\n 左足を下げる\n左腕を下げる 右腕を下げる\nジャンプする";
			break;
		case 7:
			answer = "loop3\n左足を上げる\n左足を下げる\n右足を上げる\n右足を下げる\nここまで";
			break;
		case 8:
			answer = "loop3\n左足を上げる\n左足を下げる\n右足を上げる\n右足を下げる\nここまで";
			break;
		case 9:
			answer = "loop3\n左足を上げる\n左足を下げる\n右足を上げる\n右足を下げる\nここまで\n loop2\n左腕を上げる 右腕を上げる\n 左腕を下げる 右腕を下げる\nここまで";
			break;
		case 10:
			answer = "loop3\n loop2\n 左腕を上げる 右腕を上げる\n左腕を下げる 右腕を下げる\nここまで\nジャンプする\nここまで";
			break;
		case 11:
			answer = "loop2\n左腕を上げる\n左腕を下げる\nここまで";
			break;
		case 12:
			answer = "loop2\n左腕を上げる\n左腕を下げる\nここまで\nloop2\n右腕を上げる\n右腕を下げる\nここまで";
			break;
		case 13:
			answer = "loop2\nloop2\n左腕を上げる\n左腕を下げる\nここまで\n右腕を上げる\n右腕を下げる\nここまで";
			break;
		default:
			answer = "null";
			break;
		}
		return answer;
	}
}
