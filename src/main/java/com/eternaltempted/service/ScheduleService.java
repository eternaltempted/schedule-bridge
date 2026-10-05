package com.eternaltempted.service;

import com.eternaltempted.model.Lesson;
import com.eternaltempted.model.Schedule;
import com.eternaltempted.schedule.ScheduleProvider;
import com.eternaltempted.util.AcademicWeekCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@EnableCaching
public class ScheduleService {

    private final Logger log = LoggerFactory.getLogger(ScheduleService.class);
    private final ScheduleProvider provider;
    private final Clock clock;

    public ScheduleService (ScheduleProvider provider, Clock clock) {
        this.provider = provider;
        this.clock = clock;
    }

    public Schedule getWeekSchedule(int week) throws IOException {
        log.info("Fetching schedule for week {}", week);
        return provider.getSchedule(week);
    }

    public List<Lesson> getScheduleByWeekday(
            int week,
            DayOfWeek day) throws IOException {
        log.info("Fetching schedule for weekday {} (week {})", day, week);
        return provider.getSchedule(week)
                .getLessonsByWeekday(day);
    }

    public List<Lesson> getTodaySchedule() throws IOException {

        LocalDate now = LocalDate.now(clock);
        int week = AcademicWeekCalculator.getAcademicWeek(now);

        log.info("Fetching schedule for today (date={}, dayOfWeek={}, week={})",
                now,
                now.getDayOfWeek(),
                week
        );

        return provider.getSchedule(week)
                .getLessonsByWeekday(now.getDayOfWeek());
    }

    public Optional<Lesson> getNextLesson() throws IOException {
        log.info("Fetching schedule to get the next lesson...");

        LocalDateTime now = LocalDateTime.now(clock);
        int week = AcademicWeekCalculator.getAcademicWeek(now.toLocalDate());

        Optional<Lesson> lesson = provider.getSchedule(week)
                .getNextLesson(now);

        if (lesson.isEmpty()) {
            return provider.getSchedule(week + 1)
                    .getNextLesson(now);
        }

        return lesson;
    }
}
