package com.eternaltempted.schedule;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpConnectTimeoutException;

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
                    "The week must be between 1 and 52, got: %d".formatted(week)
            );
        }

        log.info("Requesting schedule data...");

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
                    exception.getStatusCode(), week, exception
            );
            throw new IOException(
                    "Could not fetch data for week=" + week
                            + ", HTTP status=" + exception.getStatusCode(),
                    exception
            );
        } catch (HttpConnectTimeoutException exception) {
            log.warn(
                    "Connection timed out while fetching schedule for week={}",
                    week, exception
            );
            throw exception;
        } catch (IOException exception) {
            log.error(
                    "I/O failure while fetching schedule for week={}",
                    week, exception
            );
            throw exception;
        }
    }

}
