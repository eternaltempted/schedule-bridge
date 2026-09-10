package com.eternaltempted.service;

import com.eternaltempted.model.Lesson;
import com.eternaltempted.model.Schedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@EnableCaching
public class ScheduleService {

    private final Logger log = LoggerFactory.getLogger(ScheduleService.class);
    private final ScheduleProvider provider;

    public ScheduleService (ScheduleProvider provider) {
        this.provider = provider;
    }

    public Schedule getWeekSchedule(int week) throws IOException {
        log.info("Fetching schedule for week {}", week);
        return provider.getSchedule(week);
    }

    public List<Lesson> getScheduleByWeekday(
            int week,
            DayOfWeek day) throws IOException {
        log.info("Fetching schedule for day {}", day);
        return provider.getSchedule(week).getLessonsByWeekday(day);
    }

    public Optional<Lesson> getNextLesson(
            int week
    ) throws IOException {
        log.info("Fetching schedule to get the next lesson...");
        return provider.getSchedule(week).getNextLesson();
    }
}
