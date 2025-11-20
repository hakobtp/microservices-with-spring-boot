package com.htp.microservices.api.composite.models;

import com.htp.microservices.api.core.course.CourseDifficultyLevel;

import java.util.List;

public record CourseAggregate(
        Long courseId,
        String title,
        String description,
        CourseDifficultyLevel difficultyLevel,
        ServiceAddresses serviceAddresses,
        List<ChapterSummary> chapters
) {
}