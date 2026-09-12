package com.eternaltempted.service;

import com.eternaltempted.model.Lesson;
import com.eternaltempted.model.Schedule;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class GoogleCalendarService {

    private final Calendar calendar;

    public GoogleCalendarService(Calendar calendar) {
        this.calendar = calendar;
    }

    // TODO: lesson synchronisation with conversion of LocalDateTime to DateTime to be used for event creation.

//    public void syncSchedule(Schedule schedule) throws IOException {
//        for (Lesson lesson : schedule.getAllLessons()) {
//            Event event = convertLessonToEvent(lesson);
//
//            calendar.events()
//                    .insert("primary", event)
//                    .execute();
//        }
//    }


    // TODO: Lesson conversion to Event
//    private Event convertLessonToEvent(Lesson lesson) {
//        return new Event()
//                .setSummary(lesson.subject() + ", "
//                        + lesson.lessonType() + ", "
//                        + lesson.teacher())
//                .setStart(
//                        convertDateAndTime(
//                                lesson.date().atTime(lesson.startTime())
//                        )
//                )
//                .setEnd();
//    }
//
//    private EventDateTime convertDateAndTime(LocalDateTime dateTime) {
//        return new EventDateTime()
//                .setDateTime(dateTime);
//    }

    // Used for testing only
    public Event createTestEvent() throws IOException {
        Event event = new Event()
                .setSummary("Example")
                .setDescription("Lecture");

        DateTime startDateTime = new DateTime("2026-09-12T09:00:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Europe/Paris");
        event.setStart(start);

        DateTime endDateTime = new DateTime("2026-09-12T12:00:00");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Europe/Paris");
        event.setEnd(end);

        return calendar.events()
                .insert("primary", event)
                .execute();
    }
}
