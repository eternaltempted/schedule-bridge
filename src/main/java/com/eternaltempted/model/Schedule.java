package com.eternaltempted.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class Schedule {

    private final Map<LocalDate, List<Lesson>> lessons;

    public Schedule() {
        this.lessons = new TreeMap<>();
    }

    public void addLesson(Lesson lesson) {
        Objects.requireNonNull(lesson, "Lesson cannot be null");

        lessons.computeIfAbsent(lesson.date(), date ->
                new ArrayList<>()
        ).add(lesson);
    }

    public Optional<Lesson> getLessonByDateAndNumber(LocalDate date, int lessonNumber) {
        return lessons.getOrDefault(date, List.of()).stream()
                .filter(lesson -> lesson.lessonNumber() == lessonNumber)
                .findFirst();
    }

    // is currently used only for Google Calendar API implementation
    public List<Lesson> getAllLessons() {
        return lessons.values()
                .stream()
                .flatMap(List::stream)
                .toList();
    }

    public List<Lesson> getLessonsByWeekday(DayOfWeek day) {
        return lessons.entrySet()
                .stream()
                .filter(lesson -> lesson.getKey().getDayOfWeek() == day)
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(List.of());
    }

    @JsonIgnore
    public Optional<Lesson> getNextLesson(LocalDateTime now) {
        return lessons.values()
                .stream()
                .flatMap(List::stream)
                .filter(lesson -> lesson.date().atTime(lesson.startTime()).isAfter(now))
                .findFirst();
    }
}
