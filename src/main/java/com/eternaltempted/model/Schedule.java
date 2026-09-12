package com.eternaltempted.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public Optional<Lesson> getNextLesson(LocalDate today, LocalDateTime now) {
        for (Map.Entry<LocalDate, List<Lesson>> entry : lessons.entrySet()) {

            if (!entry.getKey().isBefore(today)) {
                Optional<Lesson> nextLesson = entry.getValue()
                        .stream().filter(lesson -> lesson.date()
                                .atTime(lesson.startTime()).isAfter(now))
                        .findFirst();

                if (nextLesson.isPresent()) return nextLesson;
            }
        }

        return Optional.empty();
    }
}
