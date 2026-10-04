package com.eternaltempted.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("calendar_events")
public class CalendarEventMapping {

    @Id
    private String id;

    private final LocalDate date;
    private final int lessonNumber;

    private String eventId;

    public CalendarEventMapping(LocalDate date,
                                int lessonNumber,
                                String eventId) {
        this.date = date;
        this.lessonNumber = lessonNumber;
        this.eventId = eventId;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public int getLessonNumber() {
        return this.lessonNumber;
    }

    public String getEventId() {
        return this.eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

}
