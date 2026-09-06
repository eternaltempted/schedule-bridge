package com.eternaltempted.model;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class Schedule {

    private final List<Lesson> lessons;

    public Schedule() {
        this.lessons = new ArrayList<>();
    }

    public void addLesson(Lesson lesson) {
        this.lessons.add(lesson);
    }

    public List<Lesson> getWeekSchedule() {
        return lessons;
    }

    public List<Lesson> getScheduleForDay(DayOfWeek day) {
        return lessons.stream()
                .filter(lesson -> lesson.getDate().getDayOfWeek() == day)
                .toList();
    }

}
