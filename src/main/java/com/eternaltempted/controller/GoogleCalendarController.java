package com.eternaltempted.controller;

import com.eternaltempted.service.GoogleCalendarService;
import com.google.api.services.calendar.model.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/calendar")
public class GoogleCalendarController {

    private final GoogleCalendarService googleCalendarService;

    public GoogleCalendarController(GoogleCalendarService googleCalendarService) {
        this.googleCalendarService = googleCalendarService;
    }

    // testing API endpoint to post the event and get its id as the HTTP response
    @PostMapping("/test-event")
    public ResponseEntity<String> createTestEvent() throws IOException {
        Event event = googleCalendarService.createTestEvent();
        return ResponseEntity.ok(event.getId());
    }

}
