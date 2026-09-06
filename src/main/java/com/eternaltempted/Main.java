package com.eternaltempted;

import com.eternaltempted.service.ScheduleFetcher;
import com.eternaltempted.service.ScheduleScraper;
import com.eternaltempted.util.ScheduleProperties;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        ScheduleFetcher fetcher = new ScheduleFetcher();
        ScheduleScraper scraper = new ScheduleScraper();
        ScheduleProperties props = new ScheduleProperties();

        Document doc = fetcher.fetch(
                props.getGroup(),
                props.getStudent(),
                2
        );

        System.out.println(doc);
    }
}