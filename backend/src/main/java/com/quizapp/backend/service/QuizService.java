package com.quizapp.backend.service;

import com.quizapp.backend.dto.OptionDto;
import com.quizapp.backend.dto.QuestionDto;
import com.quizapp.backend.entity.Question;
import com.quizapp.backend.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService {
    private final QuestionRepository questionRepository;

    public QuizService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<QuestionDto> getQuestionForQuiz(Long quizId) {
        List<Question> questions = questionRepository.findByQuizId(quizId);

        return questions.stream()
                .map(ques -> new QuestionDto(
                        ques.getId(),
                        ques.getText(),
                        ques.getOptions().stream()
                                .map(opt -> new OptionDto(opt.getId(), opt.getText()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }
}
