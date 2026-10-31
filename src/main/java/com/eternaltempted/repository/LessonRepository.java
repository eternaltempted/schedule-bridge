package com.eternaltempted.repository;

import com.eternaltempted.model.LessonDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface LessonRepository extends MongoRepository<LessonDTO, String> {
   LessonDTO findLessonByDateAndLessonNumber(LocalDate date, int lessonNumber);
}
