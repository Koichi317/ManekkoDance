package net.exkazuu.mimicdance;

public class Timer {
    private static long start;
    private static long end;

    public static void start() {
        start = System.currentTimeMillis();
    }

    public static long stop() {
        end = System.currentTimeMillis();
        return end - start;
    }

    public static long getTime() {
        return end - start;
    }
}
