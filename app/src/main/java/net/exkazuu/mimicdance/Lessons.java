package net.exkazuu.mimicdance;

public class Lessons {
    private static String[] coccoCodes = { // hard
        "左腕を上げる右腕を上げる\n左腕を下げる\n右腕を下げる",
        "左腕を上げる\n左腕を下げる\n右腕を上げる\n右腕を下げる\n左腕を上げる\n左腕を下げる\n右腕を上げる\n右腕を下げる\n左腕を上げる\n左腕を下げる\n右腕を上げる\n右腕を下げる",
        "くりかえし3\n左腕を上げる\n左腕を下げる\n右腕を上げる\n右腕を下げる\nここまで",
        "くりかえし3\n左腕を上げる\n左腕を下げる\nここまで\nくりかえし3\n右腕を上げる\n右腕を下げる\nここまで",
        "右腕を上げる\nもしも黄色\n右腕を下げる\nもしくは\n左腕を上げる\nもしおわり",
        "くりかえし3\nもしも黄色\n右腕を上げる\n右腕を下げる\nもしくは\n左腕を上げる\n左腕を下げる\nもしおわり\nここまで",
        "くりかえし2\nもしも黄色\n左腕を上げる\n左腕を下げる\nもしくは\n右腕を上げる\n右腕を下げる\nもしおわり\nここまで",};

    private static int[] limitations = {10, 12, 10, 8, 10, 10, 10}; // hard

    public static String getCoccoCode(int lessonNumber) {
        if (!(1 <= lessonNumber && lessonNumber <= coccoCodes.length)) {
            throw new IndexOutOfBoundsException(
                "lessonNumber (" + lessonNumber + ") should be in 1-" + coccoCodes.length);
        }
        return coccoCodes[lessonNumber - 1];
    }

    public static int getLimitation(int lessonNumber) {
        return limitations[lessonNumber - 1];
    }

    public static int getLessonCount() {
        return coccoCodes.length;
    }
}
