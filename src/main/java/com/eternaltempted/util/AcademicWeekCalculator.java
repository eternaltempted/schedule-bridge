package com.eternaltempted.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class AcademicWeekCalculator {

    private static final LocalDate ACADEMIC_YEAR_START =
            LocalDate.of(2026, 8, 31);

    public static int getAcademicWeek(LocalDate date) {
        long weeksPassed = ChronoUnit.WEEKS.between(
                ACADEMIC_YEAR_START,
                date
        );

        return (int) weeksPassed + 1;
    }
}
