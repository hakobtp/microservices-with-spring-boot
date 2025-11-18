package com.htp.microservices.api.core.chapter;

public record Chapter(
        Long chapterId,
        Long courseId,
        String title,
        String content,
        String serviceAddress
) {
}