package com.eternaltempted.controller;

import com.eternaltempted.model.Lesson;
import com.eternaltempted.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService service) {
        this.scheduleService = service;
    }

    @GetMapping("/schedule")
    public ResponseEntity<Object> getLessons(
            @RequestParam("week") int week,
            @RequestParam(value = "day", required = false) DayOfWeek day
    ) throws IOException {

        if (day == null) {
            return ResponseEntity.ok(
                    scheduleService.getWeekSchedule(week)
            );
        }

        return ResponseEntity.ok(
                scheduleService.getScheduleByWeekday(week, day)
        );
    }

    @GetMapping("/schedule/today")
    public ResponseEntity<List<Lesson>> getTodaySchedule() throws IOException {
        return ResponseEntity.ok(
                scheduleService.getTodaySchedule()
        );
    }

    @GetMapping("/schedule/next")
    public ResponseEntity<Lesson> getNextLesson() throws IOException {
        return ResponseEntity.of(
                scheduleService.getNextLesson()
        );
    }
}
