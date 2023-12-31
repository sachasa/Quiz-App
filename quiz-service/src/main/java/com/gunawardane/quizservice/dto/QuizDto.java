package com.gunawardane.quizservice.dto;

import lombok.Data;

@Data
public class QuizDto {
    String category;
    Integer numOfQuestions;
    String title;
}
