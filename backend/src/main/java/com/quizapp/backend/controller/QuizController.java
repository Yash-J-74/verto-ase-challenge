package com.quizapp.backend.controller;

import com.quizapp.backend.dto.QuestionDto;
import com.quizapp.backend.dto.SubmitRequestDto;
import com.quizapp.backend.dto.SubmitResponseDto;
import com.quizapp.backend.service.AttemptService;
import com.quizapp.backend.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {
    private final QuizService quizService;
    private final AttemptService attemptService;

    public QuizController(QuizService quizService, AttemptService attemptService) {
        this.quizService = quizService;
        this.attemptService = attemptService;
    }

    @GetMapping("/{quizId}/questions")
    public ResponseEntity<List<QuestionDto>> getQuestions(@PathVariable Long quizId) {
        return ResponseEntity.ok(quizService.getQuestionForQuiz(quizId));
    }

    @PostMapping("/{quizId}/submit")
    public ResponseEntity<SubmitResponseDto> submitAnswer(
            @PathVariable Long quizId,
            @RequestBody SubmitRequestDto request
            ) {
        return ResponseEntity.ok(attemptService.submitAnswer(quizId, request));
    }
}
