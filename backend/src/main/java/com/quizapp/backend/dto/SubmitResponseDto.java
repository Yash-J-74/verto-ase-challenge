package com.quizapp.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SubmitResponseDto {
    private int score;
    private int total;
    private List<AnswerResultDto> results;
}
