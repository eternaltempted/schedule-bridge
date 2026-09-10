package com.eternaltempted.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @JsonIgnore
    public Optional<Lesson> getNextLesson() {

        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();

        for (Map.Entry<LocalDate, List<Lesson>> entry : lessons.entrySet()) {

            if (!entry.getKey().isBefore(today)) {
                Optional<Lesson> nextLesson = entry.getValue()
                        .stream().filter(lesson -> lesson.getDate()
                                .atTime(lesson.getStartTime()).isAfter(now))
                        .findFirst();

                if (nextLesson.isPresent()) return nextLesson;
            }
        }

        return Optional.empty();
    }
}
