package com.eternaltempted.util;

import java.time.LocalTime;

public class LessonInfoParser {

    public static int parseLessonNumber(String text) {
        return Integer.parseInt(text.substring(0, 1));
    }

    public static LocalTime parseStartTime(String text) {
        String[] times = text.split(" - | ");
        return LocalTime.parse(times[0]);
    }

    public static LocalTime parseEndTime(String text) {
        String[] times = text.split(" - | ");
        return LocalTime.parse(times[times.length - 1]);
    }

}
