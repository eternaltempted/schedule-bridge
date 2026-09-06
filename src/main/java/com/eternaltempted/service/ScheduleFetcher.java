package com.eternaltempted.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class ScheduleFetcher {

    private static final String BASE_URL = "http://rozklad.hneu.edu.ua/schedule/schedule";

    public Document fetch(String group, String student, int week) throws IOException {
        return Jsoup.connect(BASE_URL)
                .data("group", group)
                .data("week", String.valueOf(week))
                .data("student", student)
                .get();
    }

}
