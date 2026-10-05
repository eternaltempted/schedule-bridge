package com.eternaltempted.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class AcademicWeekCalculator {

    public static int getAcademicWeek(LocalDate date) {

        LocalDate start = getAcademicYearStart(date);

        long weeksPassed = ChronoUnit.WEEKS.between(
                start,
                date
        );

        return Math.toIntExact(weeksPassed) + 1;
    }

    public static LocalDate getAcademicYearStart(LocalDate date) {
        LocalDate academicYearBoundary = LocalDate.of(date.getYear(), 9, 1);

        if (date.isBefore(academicYearBoundary)) {
            academicYearBoundary = academicYearBoundary.minusYears(1);
        }

        return academicYearBoundary.with(
                TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)
        );
    }
}
