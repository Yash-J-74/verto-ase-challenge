package com.quizapp.backend.config;

import com.quizapp.backend.entity.Option;
import com.quizapp.backend.entity.Question;
import com.quizapp.backend.entity.Quiz;
import com.quizapp.backend.repository.QuizRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(QuizRepository quizRepo) {
        return args -> {
            if (quizRepo.count() == 0) {
                Quiz quiz = new Quiz();
                quiz.setTitle("Sample Quiz");
                quiz.setDescription("Just a demo quiz");

                Question q1 = new Question();
                q1.setText("What is 2 + 2?");
                q1.setQuiz(quiz);

                Option o1 = new Option(); o1.setText("3"); o1.setCorrect(false); o1.setQuestion(q1);
                Option o2 = new Option(); o2.setText("4"); o2.setCorrect(true);  o2.setQuestion(q1);
                Option o3 = new Option(); o3.setText("5"); o3.setCorrect(false); o3.setQuestion(q1);
                Option o4 = new Option(); o4.setText("22");o4.setCorrect(false); o4.setQuestion(q1);
                q1.setOptions(Arrays.asList(o1, o2, o3, o4));

                quiz.setQuestions(Arrays.asList(q1));

                quizRepo.save(quiz);

                System.out.println("✅ Sample quiz inserted!");
            }
        };
    }
}

