package com.eternaltempted.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.jspecify.annotations.NonNull;

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
                     String teacher) {

    public Lesson(LocalDate date,
                  int lessonNumber,
                  String startTime,
                  String endTime,
                  String subject,
                  String lessonType,
                  String teacher) {
        this(date, lessonNumber, LocalTime.parse(startTime), LocalTime.parse(endTime), subject, lessonType, teacher);
    }
}
