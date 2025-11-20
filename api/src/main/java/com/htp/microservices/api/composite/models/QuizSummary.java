package com.htp.microservices.api.composite.models;

import java.util.List;

public record QuizSummary(
        Long quizId,
        String question,
        String correctAnswer,
        List<String> answerOptions) {
}