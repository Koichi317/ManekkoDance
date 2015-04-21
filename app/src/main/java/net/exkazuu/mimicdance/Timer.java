package net.exkazuu.mimicdance;

public class Timer {
	private static long start;
	private static long end;

	public static void startTimer() {
		start = System.currentTimeMillis();
	}

	public static void stopTimer() {
		end = System.currentTimeMillis();
	}

	public static String getResultTime() {
		long t = (end - start) / 1000;
		long min = t / 60;
		long sec = t % 60;

		String result = "タイムは" + min + "分" + sec + "秒です。";
		return result;
	}
}
