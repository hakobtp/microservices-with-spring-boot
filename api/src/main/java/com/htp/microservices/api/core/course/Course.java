package com.htp.microservices.api.core.course;

public record Course(
        Long courseId,
        String title,
        String description,
        String serviceAddress,
        CourseDifficultyLevel difficultyLevel
) {
}