package com.eternaltempted.repository;

import com.eternaltempted.model.CalendarEventMapping;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface LessonRepository extends MongoRepository<CalendarEventMapping, String> {
   CalendarEventMapping findLessonByDateAndLessonNumber(LocalDate date, int lessonNumber);
}
