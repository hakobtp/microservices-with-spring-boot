package com.htp.microservices.api.composite.models;

import java.util.List;

public record ChapterSummary(
        Long chapterId,
        String title,
        String content,
        List<QuizSummary> quizzes) {
}