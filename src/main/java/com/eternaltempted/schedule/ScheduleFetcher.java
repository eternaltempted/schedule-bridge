package com.eternaltempted.schedule;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ScheduleFetcher {

    private static final Logger log = LoggerFactory.getLogger(ScheduleFetcher.class);

    private static final int TIMEOUT_MS = 10_000;
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36";

    public Document fetch(String URL, String group, String student, int week) throws IOException {

        if (URL == null || URL.isBlank()) {
            throw new IllegalArgumentException(
                    "URL cannot be null or empty"
            );
        }

        if (group == null || group.isBlank()) {
            throw new IllegalArgumentException(
                    "Group cannot be null or empty"
            );
        }

        if (student == null || student.isBlank()) {
            throw new IllegalArgumentException(
                    "Student cannot be null or empty"
            );
        }

        if (week < 1 || week > 52) {
            throw new IllegalArgumentException(
                    "The week should be between 1 and 52, got: " + week
            );
        }

        log.info(
                "Attempting to fetch the schedule..."
        );

        try {
            Document doc = Jsoup.connect(URL)
                    .data("group", group)
                    .data("week", String.valueOf(week))
                    .data("student", student)
                    .timeout(TIMEOUT_MS)
                    .userAgent(USER_AGENT)
                    .get();

            log.debug("Successfully fetched schedule HTML document.");
            return doc;
        } catch (HttpStatusException exception) {
            log.error(
                    "HTTP error {} while fetching schedule for week={}",
                    exception.getStatusCode(), week
            );
            throw new IOException(
                    "Could not fetch data, HTTP status: " + exception.getStatusCode(),
                    exception
            );
        } catch (IOException exception) {
            log.error(
                    "Network failure connecting to the schedule portal."
            );
            throw exception;
        }
    }

}
