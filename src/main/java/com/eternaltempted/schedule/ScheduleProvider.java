package com.eternaltempted.schedule;

import com.eternaltempted.config.ScheduleProperties;
import com.eternaltempted.model.Schedule;
import org.jsoup.nodes.Document;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ScheduleProvider {
    private final ScheduleFetcher scheduleFetcher;
    private final ScheduleScraper scheduleScraper;
    private final ScheduleProperties properties;

    public ScheduleProvider(
            ScheduleFetcher scheduleFetcher,
            ScheduleScraper scheduleScraper,
            ScheduleProperties properties
    ) {
        this.scheduleFetcher = scheduleFetcher;
        this.scheduleScraper = scheduleScraper;
        this.properties = properties;
    }

    @Cacheable("schedules")
    public Schedule getSchedule(int week) throws IOException {
        Document doc = scheduleFetcher.fetch(
                properties.getURL(),
                properties.getGroup(),
                properties.getStudent(),
                week
        );

        return scheduleScraper.parse(doc);
    }
}
