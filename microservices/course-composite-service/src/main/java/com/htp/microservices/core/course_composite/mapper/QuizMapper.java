package com.htp.microservices.core.course_composite.mapper;

import com.htp.microservices.api.composite.models.QuizSummary;
import com.htp.microservices.api.core.quiz.Quiz;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuizMapper {

    public List<QuizSummary> toQuizSummaries(List<Quiz> quizzes) {
        return quizzes.stream()
                .map(this::toQuizSummary)
                .toList();
    }

    public QuizSummary toQuizSummary(Quiz quiz) {
        return new QuizSummary(
                quiz.quizId(),
                quiz.question(),
                quiz.correctAnswer(),
                quiz.answerOptions());
    }

}
