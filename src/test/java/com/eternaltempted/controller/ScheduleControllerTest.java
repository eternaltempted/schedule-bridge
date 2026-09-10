package com.eternaltempted.controller;

import com.eternaltempted.model.Lesson;
import com.eternaltempted.service.ScheduleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ScheduleController.class)
public class ScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ScheduleService scheduleService;

    @Test
    public void shouldReturnNextLesson() throws Exception
    {
        Lesson lesson = new Lesson(
                LocalDate.of(2026, 9, 12),
                3,
                "12:10",
                "13:45",
                "English",
                "Lecture",
                "Teacher #39"
        );

        when(scheduleService.getNextLesson(anyInt()))
                .thenReturn(Optional.of(lesson));

        mockMvc.perform(get("/api/schedule/next"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").value("2026-09-12"))
                .andExpect(jsonPath("$.lessonNumber").value(3))
                .andExpect(jsonPath("$.subject").value("English"))
                .andExpect(jsonPath("$.lessonType").value("Lecture"))
                .andExpect(jsonPath("$.teacher").value("Teacher #39"));
    }

    @Test
    public void shouldHandleNoNextLesson() throws Exception
    {
        when(scheduleService.getNextLesson(anyInt()))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/schedule/next"))
                .andExpect(status().isNotFound());
    }
}
