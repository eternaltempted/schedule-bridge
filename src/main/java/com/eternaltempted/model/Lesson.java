package com.eternaltempted.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDate;
import java.time.LocalTime;

@JsonPropertyOrder({
        "date",
        "lessonNumber",
        "startTime",
        "endTime",
        "subject",
        "lessonType",
        "teacher"
})
public record Lesson(LocalDate date,
                     int lessonNumber,
                     LocalTime startTime,
                     LocalTime endTime,
                     String subject,
                     String lessonType,
                     String teacher){
}
