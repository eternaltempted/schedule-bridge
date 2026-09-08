package com.eternaltempted.service;

import com.eternaltempted.model.Lesson;
import com.eternaltempted.model.Schedule;
import com.eternaltempted.config.ScheduleProperties;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleFetcher fetcher;
    private final ScheduleScraper scraper;
    private final ScheduleProperties properties;

    public ScheduleService (ScheduleFetcher fetcher,
                            ScheduleScraper scraper,
                            ScheduleProperties properties) {
        this.fetcher = fetcher;
        this.scraper = scraper;
        this.properties = properties;
    }

    public Schedule getSchedule(int week) throws IOException {

        String group = properties.getGroup();
        String student = properties.getStudent();

        Document doc = fetcher.fetch(
                group,
                student,
                week
        );

        return scraper.parse(doc);
    }

    public List<Lesson> getScheduleByWeekday(
            int week,
            String day) throws IOException{
        Schedule schedule = getSchedule(week);

        return schedule.getLessonsByWeekday(DayOfWeek.valueOf(day.toUpperCase()));
    }
}
