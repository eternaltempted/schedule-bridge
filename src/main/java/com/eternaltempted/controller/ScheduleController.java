package com.eternaltempted.controller;

import com.eternaltempted.service.ScheduleService;
import com.eternaltempted.util.AcademicWeekCalculator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;

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
            @RequestParam(value = "day", required = false) String day
    ) throws IOException {

        if (day == null) {
            return scheduleService.getSchedule(week);
        }

        return scheduleService.getScheduleByWeekday(week, day);
    }

    @GetMapping("/schedule/today")
    public Object getLessonsToday() throws IOException {
        LocalDate today = LocalDate.now();
        int academicWeek = AcademicWeekCalculator.getAcademicWeek(today);
        return scheduleService.getScheduleByWeekday(academicWeek, today.getDayOfWeek().toString());
    }

}
