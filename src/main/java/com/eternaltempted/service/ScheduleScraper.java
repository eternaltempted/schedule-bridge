package com.eternaltempted.service;

import com.eternaltempted.model.Lesson;
import com.eternaltempted.model.Schedule;
import com.eternaltempted.util.DateParser;
import com.eternaltempted.util.LessonInfoParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleScraper {

    public Schedule parse(Document doc) {
        Schedule schedule = new Schedule();

        Element table = doc.getElementsByTag("table").first();
        Elements rows = table.select("> tbody > tr");

        Element headerRow = rows.first();
        Elements columns = headerRow.select("#header-cell-table");

        for (int day = 0; day < columns.size(); day++) {
            Element header = columns.get(day);

            LocalDate selectedDate = DateParser.parse(header.getElementById("date").text());

            for (int row = 1; row < rows.size(); row++) {
                Elements cells = rows.get(row).select("> td#cell");
                Element lessonInfo = rows.get(row).selectFirst(".pair");
                String lessonText = lessonInfo.text();

                Element timing = lessonInfo.selectFirst("#pair-timing");
                String timingText = timing.text();

                Element cell = cells.get(day);

                if (cell.selectFirst("#empty") != null) {
                    continue;
                }

                int lessonNumber = LessonInfoParser.parseLessonNumber(lessonText);
                LocalTime startTime = LessonInfoParser.parseStartTime(timingText);
                LocalTime endTime = LessonInfoParser.parseEndTime(timingText);

                String subject = cell.selectFirst("#subject").text();
                String lessonType = cell.selectFirst("#lessonType").text();
                String teacher = cell.selectFirst("#schedule-link").text();

                schedule.addLesson(
                        new Lesson(
                                selectedDate,
                                lessonNumber,
                                startTime,
                                endTime,
                                subject,
                                lessonType,
                                teacher
                        )
                );
            }
        }

        return schedule;
    }
}
