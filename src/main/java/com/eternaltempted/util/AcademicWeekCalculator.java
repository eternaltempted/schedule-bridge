package com.eternaltempted.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class AcademicWeekCalculator {

    public static int getAcademicWeek(LocalDate date) {
        long weeksPassed = ChronoUnit.WEEKS.between(
                getAcademicYearStart(date),
                date
        );

        return (int) weeksPassed + 1;
    }

    public static LocalDate getAcademicYearStart(LocalDate now) {
        LocalDate academicYearBoundary = LocalDate.of(now.getYear(), 9, 1);

        return !now.isBefore(academicYearBoundary)
                ? academicYearBoundary
                : academicYearBoundary.minusYears(1);
    }
}
