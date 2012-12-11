package jp.eclipcebook;

public class LessonData {
	public static String getLessonData(int number) {
		
		String answer = null;
		
		switch(number) {
		case 1:
			answer = "¶˜r‚ğã‚°‚é\n¶˜r‚ğ‰º‚°‚é\n‰E˜r‚ğã‚°‚é\n‰E˜r‚ğ‰º‚°‚é";
			break;
		case 2:
			answer = "¶˜r‚ğã‚°‚é ‰E˜r‚ğã‚°‚é\n¶˜r‚ğ‰º‚°‚é\n‰E˜r‚ğ‰º‚°‚é\n¶˜r‚ğã‚°‚é\n‰E˜r‚ğã‚°‚é\n¶˜r‚ğ‰º‚°‚é ‰E˜r‚ğ‰º‚°‚é\nƒWƒƒƒ“ƒv‚·‚é";
			break;
		case 3:
			answer = "¶˜r‚ğã‚°‚é\n¶˜r‚ğ‰º‚°‚é\n‰E˜r‚ğã‚°‚é\n‰E˜r‚ğ‰º‚°‚é";
			break;
		case 4:
			answer = "¶˜r‚ğã‚°‚é\n¶˜r‚ğ‰º‚°‚é\n‰E˜r‚ğã‚°‚é\n‰E˜r‚ğ‰º‚°‚é";
			break;
		default:
			answer = "null";
			break;
		}
		return answer;
	}
}
