package com.eternaltempted.service;

import com.eternaltempted.model.Lesson;
import com.eternaltempted.model.Schedule;
import com.eternaltempted.util.DateParser;
import com.eternaltempted.util.LessonInfoParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class ScheduleScraper {

    public Schedule parse(Document doc) {
        Element table = doc.getElementsByTag("table").first();
        if (table == null) {
            throw new IllegalStateException(
                    "Schedule table missing in HTML response."
            );
        }

        Element mainTbody = table.selectFirst("> tbody");
        List<Element> rows = (mainTbody != null)
                ? mainTbody.children().stream()
                    .filter(child -> "tr".equalsIgnoreCase(child.tagName()))
                    .toList()
                : List.of();

        if (rows.isEmpty()) {
            throw new IllegalStateException(
                    "No rows found in schedule table body."
            );
        }

        Element headerRow = rows.getFirst();

        if (headerRow == null) {
            throw new IllegalStateException(
                    "No header row found in the 'rows' section."
            );
        }

        Elements columns = headerRow.select("#header-cell-table");
        List<LocalDate> dates = columns.stream()
                .map(column -> column.selectFirst("#date"))
                .filter(Objects::nonNull)
                .map(date -> DateParser.parse(date.text()))
                .toList();

        Schedule schedule = new Schedule();

        for (int row = 1; row < rows.size(); row++) {
            Element rowElement = rows.get(row);

            Optional.ofNullable(rowElement.selectFirst(".pair")).ifPresent(info -> {
                String lessonText = info.text();
                String timingText = Optional.ofNullable(info.selectFirst("#pair-timing"))
                        .map(Element::text).orElse("");

                int lessonNumber = LessonInfoParser.parseLessonNumber(lessonText);
                LocalTime startTime = LessonInfoParser.parseStartTime(timingText);
                LocalTime endTime = LessonInfoParser.parseEndTime(timingText);

                Elements cells = rowElement.select("> td#cell, >td#weekend-cell");
                int totalDays = Math.min(dates.size(), cells.size());

                for (int day = 0; day < totalDays; day++) {
                    Element selectedCell = cells.get(day);
                    LocalDate date = dates.get(day);

                    if (selectedCell.selectFirst("#empty") != null) {
                        continue;
                    }

                    String subject = Optional.ofNullable(selectedCell.selectFirst("#subject"))
                            .map(Element::text)
                            .orElse("");

                    String lessonType = Optional.ofNullable(selectedCell.selectFirst("#lessonType"))
                            .map(Element::text)
                            .orElse("");

                    String teacher = Optional.ofNullable(selectedCell.selectFirst("a"))
                                    .map(Element::text)
                                    .orElse("");

                    schedule.addLesson(new Lesson(
                            date,
                            lessonNumber,
                            startTime,
                            endTime,
                            subject,
                            lessonType,
                            teacher
                    ));
                }
            });
        }

        return schedule;
    }
}
