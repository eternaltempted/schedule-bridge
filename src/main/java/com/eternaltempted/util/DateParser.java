package com.eternaltempted.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateParser {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern(
                    "d MMMM uuuu",
                    new Locale("uk", "UA")
            );

    public static LocalDate parse(String date) {
        return LocalDate.parse(date, FORMATTER);
    }
}
