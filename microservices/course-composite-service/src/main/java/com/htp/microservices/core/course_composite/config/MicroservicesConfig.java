package com.htp.microservices.core.course_composite.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "microservices")
public record MicroservicesConfig(
        ServiceConfig courseService,
        ServiceConfig chapterService,
        ServiceConfig quizService
) {

    public record ServiceConfig(String host, int port) {

    }

    public String getCourseServiceUrl() {
        return "http://" + courseService.host() + ":" + courseService.port() + "/api/v1/courses";
    }

    public String getChapterServiceUrl() {
        return "http://" + chapterService.host() + ":" + chapterService.port() + "/api/v1/chapters";
    }

    public String getQuizServiceUrl() {
        return "http://" + quizService.host() + ":" + quizService.port() + "/api/v1/quizzes";
    }
}