package com.eternaltempted.service;

import com.eternaltempted.model.Schedule;
import com.eternaltempted.util.ScheduleProperties;
import org.jsoup.nodes.Document;

import java.io.IOException;

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
}
