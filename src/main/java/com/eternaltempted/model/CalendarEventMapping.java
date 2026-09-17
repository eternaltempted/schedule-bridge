package com.eternaltempted.model;

import java.time.LocalDate;


public class CalendarEventMapping {

    private final LocalDate date;
    private final int lessonNumber;

    private final String eventId;

    public CalendarEventMapping(LocalDate date,
                                int lessonNumber,
                                String eventId) {
        this.date = date;
        this.lessonNumber = lessonNumber;
        this.eventId = eventId;
    }
}
