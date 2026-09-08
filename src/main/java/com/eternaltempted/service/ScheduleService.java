package com.eternaltempted.service;

import com.eternaltempted.model.Lesson;
import com.eternaltempted.model.Schedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.List;

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
        log.info("Fetching schedule for {}", day);
        return provider.getSchedule(week).getLessonsByWeekday(day);
    }
}
