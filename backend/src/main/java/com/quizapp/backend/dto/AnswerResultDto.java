package com.quizapp.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnswerResultDto {
    private Long questionId;
    private String questionText;
    private String chosenOption;
    private boolean correct;
}
