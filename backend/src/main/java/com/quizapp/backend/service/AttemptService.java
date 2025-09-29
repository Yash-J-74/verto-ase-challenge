package com.quizapp.backend.service;

import com.quizapp.backend.dto.AnswerResultDto;
import com.quizapp.backend.dto.SubmitAnswerDto;
import com.quizapp.backend.dto.SubmitRequestDto;
import com.quizapp.backend.dto.SubmitResponseDto;
import com.quizapp.backend.entity.*;
import com.quizapp.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AttemptService {
    private final QuizRepository quizRepo;
    private final QuestionRepository questionRepo;
    private final OptionRepository optionRepo;
    private final AttemptRepository attemptRepo;
    private final AttemptAnswerRepository attemptAnswerRepo;

    public AttemptService(QuizRepository quizRepo,
                          QuestionRepository questionRepo,
                          OptionRepository optionRepo,
                          AttemptRepository attemptRepo,
                          AttemptAnswerRepository attemptAnswerRepo) {
        this.quizRepo = quizRepo;
        this.questionRepo = questionRepo;
        this.optionRepo = optionRepo;
        this.attemptRepo = attemptRepo;
        this.attemptAnswerRepo = attemptAnswerRepo;
    }

    @Transactional
    public SubmitResponseDto submitAnswer(Long quizId, SubmitRequestDto request) {
        Quiz quiz = quizRepo.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        Attempt attempt = new Attempt();
        attempt.setQuiz(quiz);
        attempt.setStartedAt(LocalDateTime.now());
        attempt = attemptRepo.save(attempt);

        int score = 0;
        List<AnswerResultDto> results = new ArrayList<>();

        for (SubmitAnswerDto answer : request.getAnswers()) {
            Question question = questionRepo.findById(answer.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found."));

            Option chosenoption = optionRepo.findById(answer.getOptionId())
                    .orElseThrow(() -> new RuntimeException("Option not found."));

            boolean correct = chosenoption.isCorrect();
            if (correct) score++;

            AttemptAnswer attemptAnswer = new AttemptAnswer();
            attemptAnswer.setAttempt(attempt);
            attemptAnswer.setQuestion(question);
            attemptAnswer.setOption(chosenoption);
            attemptAnswer.setCorrect(correct);
            attemptAnswerRepo.save(attemptAnswer);

            String correctOptionText = question.getOptions()
                    .stream()
                    .filter(Option::isCorrect)
                    .findFirst()
                    .map(Option::getText)
                    .orElse("");

            results.add(new AnswerResultDto(
                    question.getId(),
                    question.getText(),
                    chosenoption.getText(),
                    correctOptionText,
                    correct
            ));
        }

        attempt.setCompletedAt(LocalDateTime.now());
        attempt.setScore(score);
        attemptRepo.save(attempt);

        return new SubmitResponseDto(score, request.getAnswers().size(), attempt.getId(), results);
    }

    @Transactional(readOnly = true)
    public SubmitResponseDto getAttemptResults(Long quizId, Long attemptId) {
        Attempt attempt = attemptRepo.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));

        if (!Objects.equals(attempt.getQuiz().getId(), quizId)) {
            throw new RuntimeException("Attempt does not belong to this quiz");
        }

        List<AnswerResultDto> results = new ArrayList<>();
        int score = 0;

        for (AttemptAnswer answer : attempt.getAnswers()) {
            boolean correct = answer.isCorrect();
            if (correct) score++;

            results.add(new AnswerResultDto(
                    answer.getQuestion().getId(),
                    answer.getQuestion().getText(),
                    answer.getOption().getText(),
                    answer.getQuestion().getOptions()
                            .stream()
                            .filter(Option::isCorrect)
                            .findFirst()
                            .map(Option::getText)
                            .orElse(""),
                    correct
            ));
        }

        // store score in attempt if not already stored
        if (attempt.getScore() == null) {
            attempt.setScore(score);
            attempt.setCompletedAt(LocalDateTime.now());
            attemptRepo.save(attempt);
        }

        return new SubmitResponseDto(
                score,
                attempt.getQuiz().getQuestions().size(),
                attempt.getId(),
                results
        );
    }

}
