package com.eternaltempted.controller;

import com.eternaltempted.model.Lesson;
import com.eternaltempted.service.ScheduleService;
import com.eternaltempted.util.AcademicWeekCalculator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService service) {
        this.scheduleService = service;
    }

    @GetMapping("/schedule")
    public Object getLessons(
            @RequestParam("week") int week,
            @RequestParam(value = "day", required = false) DayOfWeek day
    ) throws IOException {

        if (day == null) {
            return scheduleService.getWeekSchedule(week);
        }

        return scheduleService.getScheduleByWeekday(week, day);
    }

    @GetMapping("/schedule/today")
    public List<Lesson> getLessonsToday() throws IOException {
        LocalDate today = LocalDate.now();
        int academicWeek = AcademicWeekCalculator.getAcademicWeek(today);

        return scheduleService.getScheduleByWeekday(academicWeek, today.getDayOfWeek());
    }

    @GetMapping("/schedule/next")
    public ResponseEntity<Lesson> getNextLesson() throws IOException {
        LocalDate today = LocalDate.now();
        int academicWeek = AcademicWeekCalculator.getAcademicWeek(today);

        return ResponseEntity.of(
                scheduleService.getNextLesson(academicWeek)
        );
    }

}
