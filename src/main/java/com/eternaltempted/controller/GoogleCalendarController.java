package com.eternaltempted.controller;

import com.eternaltempted.model.Schedule;
import com.eternaltempted.service.GoogleCalendarService;
import com.eternaltempted.service.ScheduleService;
import com.eternaltempted.util.AcademicWeekCalculator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;

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

    @PostMapping("/sync/next")
    public ResponseEntity<Void> syncScheduleForNextWeek() throws IOException {
        Schedule schedule = scheduleService.getWeekSchedule(
                AcademicWeekCalculator.getAcademicWeek(
                        LocalDate.now().plusWeeks(1)
                )
        );

        googleCalendarService.syncSchedule(schedule);
        return ResponseEntity.ok().build();
    }
}
