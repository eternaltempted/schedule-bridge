package com.eternaltempted.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Lesson {

    private final LocalDate date;
    private final int lessonNumber;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final String subject;
    private final String lessonType;
    private final String teacher;

    public Lesson(LocalDate date,
                  int lessonNumber,
                  LocalTime startTime,
                  LocalTime endTime,
                  String subject,
                  String lessonType,
                  String teacher) {
        this.date = date;
        this.lessonNumber = lessonNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.subject = subject;
        this.lessonType = lessonType;
        this.teacher = teacher;
    }

    public Lesson(LocalDate date,
                  int lessonNumber,
                  String startTime,
                  String endTime,
                  String subject,
                  String lessonType,
                  String teacher) {
        this.date = date;
        this.lessonNumber = lessonNumber;
        this.startTime = LocalTime.parse(startTime);
        this.endTime = LocalTime.parse(endTime);
        this.subject = subject;
        this.lessonType = lessonType;
        this.teacher = teacher;
    }


    public LocalDate getDate() {
        return date;
    }

    public int getLessonNumber() {
        return lessonNumber;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getSubject() {
        return subject;
    }

    public String getLessonType() {
        return lessonType;
    }

    public String getTeacher() {
        return teacher;
    }

    @Override
    public String toString() {
        return """
           [%s (%s)] #%d: %s (%s)
           Time: %s - %s | Teacher: %s
           """.formatted(
                date,
                date.getDayOfWeek(),
                lessonNumber,
                subject,
                lessonType,
                startTime,
                endTime,
                teacher
        );
    }
}
