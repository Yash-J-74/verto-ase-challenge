package com.quizapp.backend.repository;

import com.quizapp.backend.entity.AttemptAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttemptAnswerRepository extends JpaRepository<AttemptAnswer, Long> {
//    List<AttemptAnswer> findAttemptId(Long attemptId);
}
