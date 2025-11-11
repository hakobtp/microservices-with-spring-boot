package com.htp.microservices.api.core.quiz;

import java.util.List;

public record Quiz(
        Long quizId,
        Long chapterId,
        String question,
        String correctAnswer,
        String serviceAddress,
        List<String> answerOptions
) {
}
