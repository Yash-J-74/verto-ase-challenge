package com.quizapp.backend.dto;

import lombok.Data;

@Data
public class SubmitAnswerDto {
    private Long questionId;
    private Long optionId;
}
