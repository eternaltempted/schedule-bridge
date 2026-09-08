package com.eternaltempted.util;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class LessonInfoParser {

    public static int parseLessonNumber(String text) {
        try {
            return Integer.parseInt(text.substring(0, 1));
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(
                    "Could not parse lesson number, got: " + text,
                    exception
            );
        }
    }

    public static LocalTime parseStartTime(String text) {
        String[] times = text.split(" - | ");

        try {
            return LocalTime.parse(times[0]);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(
                    "Could not parse start time, got: " + text,
                    exception
            );
        }
    }

    public static LocalTime parseEndTime(String text) {
        String[] times = text.split(" - | ");

        try {
            return LocalTime.parse(times[times.length - 1]);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(
                    "Could not parse end time, got: " + text,
                    exception
            );
        }

    }

}
