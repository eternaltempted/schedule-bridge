package com.eternaltempted.controller;

import com.eternaltempted.model.Schedule;
import com.eternaltempted.service.GoogleCalendarService;
import com.eternaltempted.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/calendar")
public class GoogleCalendarController {

    private final GoogleCalendarService googleCalendarService;
    private final ScheduleService scheduleService;

    public GoogleCalendarController(GoogleCalendarService googleCalendarService, ScheduleService scheduleService) {
        this.googleCalendarService = googleCalendarService;
        this.scheduleService = scheduleService;
    }

    @PostMapping("/sync")
    public ResponseEntity<Void> syncSchedule(
            @RequestParam("week") int week
    ) throws IOException {
        Schedule schedule = scheduleService.getWeekSchedule(week);
        googleCalendarService.syncSchedule(schedule);
        return ResponseEntity.ok().build();
    }

}
