package com.quizapp.backend.service;

import com.quizapp.backend.dto.AnswerResultDto;
import com.quizapp.backend.dto.SubmitAnswerDto;
import com.quizapp.backend.dto.SubmitRequestDto;
import com.quizapp.backend.dto.SubmitResponseDto;
import com.quizapp.backend.entity.Option;
import com.quizapp.backend.entity.Question;
import com.quizapp.backend.entity.Quiz;
import com.quizapp.backend.repository.QuizRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AttemptServiceTest {
    private QuizRepository quizRepository;
    private AttemptService attemptService;
    private Quiz quiz;
    private Question question1;
    private Question question2;
    private Option q1o1, q1o2, q2o1, q2o2;


    @BeforeEach
    void setUp() {
        quizRepository = mock(QuizRepository.class);
        attemptService = new AttemptService(quizRepository, null, null, null, null);

        quiz = new Quiz();
        quiz.setId(1L);
        quiz.setTitle("Math quiz test");

        // Question 1
        question1 = new Question();
        question1.setId(1L);
        question1.setText("What is 2 + 2?");
        q1o1 = new Option(); q1o1.setId(11L); q1o1.setText("3"); q1o1.setCorrect(false);
        q1o2 = new Option(); q1o2.setId(12L); q1o2.setText("4"); q1o2.setCorrect(true);
        question1.setOptions(Arrays.asList(q1o1, q1o2));

        // Question 2
        question2 = new Question();
        question2.setId(2L);
        question2.setText("What is 5 - 3?");
        q2o1 = new Option(); q2o1.setId(21L); q2o1.setText("1"); q2o1.setCorrect(false);
        q2o2 = new Option(); q2o2.setId(22L); q2o2.setText("2"); q2o2.setCorrect(true);
        question2.setOptions(Arrays.asList(q2o1, q2o2));

        quiz.setQuestions(Arrays.asList(question1, question2));

        when(quizRepository.findById(1L)).thenReturn(java.util.Optional.of(quiz));
    }

    @Test
    void testSubmitAnswers_AllCorrect() {
        SubmitRequestDto request = new SubmitRequestDto();
        request.setAnswers(Arrays.asList(
                new SubmitAnswerDto(1L, 12L),
                new SubmitAnswerDto(2L, 22L)
        ));

        SubmitResponseDto response = attemptService.submitAnswer(1L, request);

        assertEquals(2, response.getScore());
        assertEquals(2, response.getTotal());
        assertTrue(response.getResults().stream().allMatch(AnswerResultDto::isCorrect));
    }

    @Test
    void testSubmitAnswers_SomeWrong() {
        SubmitRequestDto request = new SubmitRequestDto();
        request.setAnswers(Arrays.asList(
                new SubmitAnswerDto(1L, 122L),  // Wrong
                new SubmitAnswerDto(2L, 22L)    // Correct
        ));

        SubmitResponseDto response = attemptService.submitAnswer(1L, request);

        assertEquals(1, response.getScore());
        assertEquals(2, response.getTotal());

        // Checking individual results
        assertFalse(response.getResults().get(0).isCorrect());
        assertFalse(response.getResults().get(1).isCorrect());
    }

    @Test
    void testSubmitAnswers_NoAnswers() {
        SubmitRequestDto request = new SubmitRequestDto();
        request.setAnswers(Collections.emptyList());

        SubmitResponseDto response = attemptService.submitAnswer(1L, request);

        assertEquals(0, response.getScore());
        assertEquals(2, response.getTotal());
    }
}
