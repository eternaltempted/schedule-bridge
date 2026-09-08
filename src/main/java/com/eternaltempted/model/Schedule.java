package com.eternaltempted.model;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Schedule {

    private final List<Lesson> lessons;

    public Schedule() {
        this.lessons = new ArrayList<>();
    }

    public void addLesson(Lesson lesson) {
        if (lesson == null) {
            throw new IllegalArgumentException(
                    "Lesson cannot be null"
            );
        }

        this.lessons.add(lesson);
    }

    public List<Lesson> getLessons() {
        return lessons.stream()
                .sorted(Comparator.comparing(Lesson::getDate)
                        .thenComparing(Lesson::getStartTime))
                .toList();
    }

    public List<Lesson> getLessonsByDay(DayOfWeek day) {
        return lessons.stream()
                .sorted(Comparator.comparing(Lesson::getStartTime))
                .filter(lesson -> lesson.getDate().getDayOfWeek() == day)
                .toList();
    }

}
