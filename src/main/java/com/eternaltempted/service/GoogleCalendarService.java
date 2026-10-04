package com.eternaltempted.service;

import com.eternaltempted.model.CalendarEventMapping;
import com.eternaltempted.model.Lesson;
import com.eternaltempted.model.Schedule;
import com.eternaltempted.repository.LessonRepository;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;

import com.google.api.services.calendar.model.Events;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.*;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class GoogleCalendarService {

    private static final ZoneId SOURCE_ZONE = ZoneId.of("Europe/Kyiv");
    private static final ZoneId TARGET_ZONE = ZoneId.of("Europe/Paris");
    private static final String CALENDAR_ID = "primary";

    private final Calendar calendar;
    private final LessonRepository repository;

    public GoogleCalendarService(Calendar calendar, LessonRepository repository) {
        this.calendar = calendar;
        this.repository = repository;
    }

    public void syncSchedule(Schedule schedule) throws IOException {

        for (Lesson lesson : schedule.getAllLessons()) {

            CalendarEventMapping mapping = repository.findLessonByDateAndLessonNumber(
                    lesson.date(),
                    lesson.lessonNumber()
            );

            if (mapping == null) {
                Event event = calendar.events()
                        .insert(CALENDAR_ID, convertLessonToEvent(lesson))
                        .execute();

                repository.insert(
                        new CalendarEventMapping(
                                lesson.date(),
                                lesson.lessonNumber(),
                                event.getId()
                        )
                );
            }
        }

        List<CalendarEventMapping> mappings = repository.findAll();

        if (mappings.isEmpty()) return;

        for (CalendarEventMapping mapping : mappings) {
            try {
                Event event = calendar.events()
                        .get(CALENDAR_ID, mapping.getEventId())
                        .execute();

                if ("cancelled".equals(event.getStatus())) {
                    recreateEvent(mapping, schedule);
                }

            } catch (GoogleJsonResponseException e) {
                if (e.getStatusCode() == 404) {
                    recreateEvent(mapping, schedule);
                } else {
                    throw e;
                }
            }
        }
    }

    private void recreateEvent(CalendarEventMapping mapping, Schedule schedule) throws IOException {
        Optional<Lesson> lesson = schedule.getLessonByDateAndNumber(
                mapping.getDate(), mapping.getLessonNumber()
        );

        if (lesson.isPresent()) {
            Lesson existingLesson = lesson.get();
            Event event = calendar.events()
                    .insert(CALENDAR_ID, convertLessonToEvent(existingLesson))
                    .execute();

            mapping.setEventId(event.getId());
            repository.save(mapping);
            return;
        }

        repository.delete(mapping);
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
        ZonedDateTime ect = localDateTime
                .atZone(SOURCE_ZONE)
                .withZoneSameInstant(TARGET_ZONE);

        return new EventDateTime()
                .setDateTime(new DateTime(ect.toInstant().toEpochMilli()))
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
