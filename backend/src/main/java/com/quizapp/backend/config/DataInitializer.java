package com.quizapp.backend.config;

import com.quizapp.backend.entity.Option;
import com.quizapp.backend.entity.Question;
import com.quizapp.backend.entity.Quiz;
import com.quizapp.backend.repository.OptionRepository;
import com.quizapp.backend.repository.QuestionRepository;
import com.quizapp.backend.repository.QuizRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(QuizRepository quizRepo, QuestionRepository questionRepo, OptionRepository optionRepo) {
        return args -> {
            if (quizRepo.count() == 0) {
                Quiz quiz = new Quiz();
                quiz.setTitle("Sample Quiz");
                quiz.setDescription("A demo quiz with multiple questions");

                // --- Question 1 ---
                Question q1 = new Question();
                q1.setText("What is 2 + 2?");
                q1.setQuiz(quiz);

                Option q1o1 = new Option(); q1o1.setText("3"); q1o1.setCorrect(false); q1o1.setQuestion(q1);
                Option q1o2 = new Option(); q1o2.setText("4"); q1o2.setCorrect(true);  q1o2.setQuestion(q1);
                Option q1o3 = new Option(); q1o3.setText("5"); q1o3.setCorrect(false); q1o3.setQuestion(q1);
                Option q1o4 = new Option(); q1o4.setText("22"); q1o4.setCorrect(false); q1o4.setQuestion(q1);
                q1.setOptions(Arrays.asList(q1o1, q1o2, q1o3, q1o4));

                // --- Question 2 ---
                Question q2 = new Question();
                q2.setText("Which planet is known as the Red Planet?");
                q2.setQuiz(quiz);

                Option q2o1 = new Option(); q2o1.setText("Earth"); q2o1.setCorrect(false); q2o1.setQuestion(q2);
                Option q2o2 = new Option(); q2o2.setText("Mars"); q2o2.setCorrect(true); q2o2.setQuestion(q2);
                Option q2o3 = new Option(); q2o3.setText("Jupiter"); q2o3.setCorrect(false); q2o3.setQuestion(q2);
                Option q2o4 = new Option(); q2o4.setText("Venus"); q2o4.setCorrect(false); q2o4.setQuestion(q2);
                q2.setOptions(Arrays.asList(q2o1, q2o2, q2o3, q2o4));

                // --- Question 3 ---
                Question q3 = new Question();
                q3.setText("What is the capital of France?");
                q3.setQuiz(quiz);

                Option q3o1 = new Option(); q3o1.setText("Berlin"); q3o1.setCorrect(false); q3o1.setQuestion(q3);
                Option q3o2 = new Option(); q3o2.setText("Paris"); q3o2.setCorrect(true); q3o2.setQuestion(q3);
                Option q3o3 = new Option(); q3o3.setText("Madrid"); q3o3.setCorrect(false); q3o3.setQuestion(q3);
                Option q3o4 = new Option(); q3o4.setText("Rome"); q3o4.setCorrect(false); q3o4.setQuestion(q3);
                q3.setOptions(Arrays.asList(q3o1, q3o2, q3o3, q3o4));

                // --- Question 4 ---
                Question q4 = new Question();
                q4.setText("Which language is primarily used for Android development?");
                q4.setQuiz(quiz);

                Option q4o1 = new Option(); q4o1.setText("Swift"); q4o1.setCorrect(false); q4o1.setQuestion(q4);
                Option q4o2 = new Option(); q4o2.setText("Kotlin"); q4o2.setCorrect(true); q4o2.setQuestion(q4);
                Option q4o3 = new Option(); q4o3.setText("Ruby"); q4o3.setCorrect(false); q4o3.setQuestion(q4);
                Option q4o4 = new Option(); q4o4.setText("C#"); q4o4.setCorrect(false); q4o4.setQuestion(q4);
                q4.setOptions(Arrays.asList(q4o1, q4o2, q4o3, q4o4));

                // Attach all questions to quiz
                quiz.setQuestions(Arrays.asList(q1, q2, q3, q4));

                // Save the quiz (cascade should handle questions and options if set)
                quizRepo.save(quiz);

                System.out.println("Sample quiz with multiple questions inserted!");
            }
        };
    }
}

