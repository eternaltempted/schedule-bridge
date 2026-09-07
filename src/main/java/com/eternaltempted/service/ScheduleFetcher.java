package com.eternaltempted.service;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class ScheduleFetcher {

    private static final String BASE_URL = "http://rozklad.hneu.edu.ua/schedule/schedule";
    private static final int TIMEOUT_MS = 5000;

    public Document fetch(String group, String student, int week) throws IOException {

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

        try {
            return Jsoup.connect(BASE_URL)
                    .data("group", group)
                    .data("week", String.valueOf(week))
                    .data("student", student)
                    .timeout(TIMEOUT_MS)
                    .get();
        } catch (HttpStatusException exception) {
            throw new IOException(
                    "Could not fetch data, HTTP status: " + exception.getStatusCode(),
                    exception
            );
        }

    }

}
