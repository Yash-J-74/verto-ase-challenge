package com.quizapp.backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class SubmitRequestDto {
    private List<SubmitAnswerDto> answers;
}
