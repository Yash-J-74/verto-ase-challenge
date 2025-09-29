package com.quizapp.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizapp.backend.entity.Option;
import com.quizapp.backend.entity.Question;
import com.quizapp.backend.entity.Quiz;
import com.quizapp.backend.repository.QuizRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

@SpringBootTest
@AutoConfigureMockMvc
public class QuizControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private ObjectMapper objectMapper;
    private Long quizId;

    @BeforeEach
    void setUp() {
        quizRepository.deleteAll();

        Quiz quiz = new Quiz();
        quiz.setTitle("Math quiz");

        Question q1 = new Question();
        q1.setText("What is 2 + 2?");

        Option op1 = new Option();
        op1.setText("3");
        op1.setCorrect(false);

        Option op2 = new Option();
        op2.setText("4");
        op2.setCorrect(true);

        q1.setOptions(Arrays.asList(op1, op2));

        Quiz saved = quizRepository.save(quiz);
        quizId = saved.getId();
    }

    @Test
    void getQuestions() throws Exception {
        mockMvc.perform(get("/api/quiz/" + quizId + "/questions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].text").value("What is 2 + 2?"))
                .andExpect(jsonPath("$[0].options[0].text").value("3"))
                .andExpect(jsonPath("$[0].options[1].text").value("4"))
                .andExpect(jsonPath("$[0].options[0].correct").doesNotExist()); // to ensure isCorrect is hidden
    }

    @Test
    void SubmitAnswers() throws Exception {
        String requestJson = """
        {
          "answers": [
            { "questionId": 1, "optionId": 2 }
          ]
        }
        """;

        mockMvc.perform(post("/api/quiz/" + quizId + "/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.score").value(1))
                .andExpect(jsonPath("$.total").value(1))
                .andExpect(jsonPath("$.results[0].correct").value(true));
    }
}
