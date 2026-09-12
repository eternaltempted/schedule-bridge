package com.eternaltempted.service;

import com.eternaltempted.model.Lesson;
import com.eternaltempted.model.Schedule;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.*;
import java.util.Arrays;

@Service
public class GoogleCalendarService {

    private static final ZoneId SOURCE_ZONE = ZoneId.of("Europe/Kyiv");
    private static final ZoneId TARGET_ZONE = ZoneId.of("Europe/Berlin");

    private final Calendar calendar;

    public GoogleCalendarService(Calendar calendar) {
        this.calendar = calendar;
    }

    public void syncSchedule(Schedule schedule) throws IOException {
        for (Lesson lesson : schedule.getAllLessons()) {
            Event event = convertLessonToEvent(lesson);

            calendar.events()
                    .insert("primary", event)
                    .execute();
        }
    }

    private Event convertLessonToEvent(Lesson lesson) {

        EventDateTime start = convertDateAndTime(lesson.date().atTime(lesson.startTime()));
        EventDateTime end = convertDateAndTime(lesson.date().atTime(lesson.endTime()));

        return new Event()
                .setSummary(getSummary(lesson))
                .setStart(start)
                .setEnd(end)
                .setReminders(getReminders());
    }

    private EventDateTime convertDateAndTime(LocalDateTime localDateTime) {
        ZonedDateTime berlinDateTime = localDateTime
                .atZone(SOURCE_ZONE)
                .withZoneSameInstant(TARGET_ZONE);

        return new EventDateTime()
                .setDateTime(
                        new DateTime(
                                berlinDateTime.toInstant().toEpochMilli()
                        )
                )
                .setTimeZone(TARGET_ZONE.getId());
    }

    private String getSummary(Lesson lesson) {
        return lesson.subject() + ", " + lesson.lessonType() + ", " + lesson.teacher();
    }

    private Event.Reminders getReminders() {
        EventReminder[] reminders = new EventReminder[] {
                new EventReminder().setMethod("popup").setMinutes(30),
                new EventReminder().setMethod("popup").setMinutes(15)
        };

        return new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminders));
    }
}
