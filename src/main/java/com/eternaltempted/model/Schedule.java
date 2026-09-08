package com.eternaltempted.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class Schedule {

    private final Map<LocalDate, List<Lesson>> lessons;

    public Schedule() {
        this.lessons = new TreeMap<>(); // guarantees chronological order
    }

    public void addLesson(Lesson lesson) {
        Objects.requireNonNull(lesson, "Lesson cannot be null");

        lessons.computeIfAbsent(lesson.getDate(), date ->
                new ArrayList<>()
        ).add(lesson);
    }

    public Map<LocalDate, List<Lesson>> getLessons() {
        return lessons;
    }

    public List<Lesson> getLessonsByWeekday(DayOfWeek day) {
        return lessons.entrySet()
                .stream()
                .filter(lesson -> lesson.getKey().getDayOfWeek() == day)
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(List.of());
    }
}
