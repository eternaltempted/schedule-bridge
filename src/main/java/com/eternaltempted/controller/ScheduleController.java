package com.eternaltempted.controller;

import com.eternaltempted.model.Lesson;
import com.eternaltempted.service.ScheduleService;
import com.eternaltempted.util.AcademicWeekCalculator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
