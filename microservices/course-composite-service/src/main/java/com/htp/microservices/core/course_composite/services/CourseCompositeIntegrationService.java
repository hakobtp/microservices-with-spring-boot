package com.htp.microservices.core.course_composite.services;

import com.htp.microservices.api.core.chapter.Chapter;
import com.htp.microservices.api.core.chapter.ChapterService;
import com.htp.microservices.api.core.course.Course;
import com.htp.microservices.api.core.course.CourseService;
import com.htp.microservices.api.core.quiz.Quiz;
import com.htp.microservices.api.core.quiz.QuizService;
import com.htp.microservices.core.course_composite.config.MicroservicesConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseCompositeIntegrationService implements CourseService, ChapterService, QuizService {

    private final RestTemplate restTemplate;
    private final MicroservicesConfig microservicesConfig;
    private final HttpClientErrorHandleService httpClientErrorHandleService;

    @Override
    public Course getCourseById(Long courseId) {
        try {
            var url = microservicesConfig.getCourseServiceUrl() + "/" + courseId;
            return restTemplate.getForObject(url, Course.class);
        } catch (HttpClientErrorException ex) {
            httpClientErrorHandleService.translateHttpClientError(ex);
            return null; // unreachable, but required by compiler
        }
    }

    @Override
    public List<Chapter> getChaptersByCourseId(Long courseId) {
        try {
            var url = microservicesConfig.getChapterServiceUrl() + "?courseId=" + courseId;
            return restTemplate.exchange(url, GET, null,
                    new ParameterizedTypeReference<List<Chapter>>() {
                    }).getBody();
        } catch (Exception ex) {
            log.warn("Got an exception while requesting chapters, return zero chapters: {}", ex.getMessage());
            return List.of();
        }
    }

    @Override
    public List<Quiz> getQuizzesByChapterId(Long chapterId) {
        try {
            var url = microservicesConfig.getQuizServiceUrl() + "?chapterId=" + chapterId;
            return restTemplate.exchange(url, GET, null,
                    new ParameterizedTypeReference<List<Quiz>>() {
                    }).getBody();
        } catch (Exception ex) {
            log.warn("Got an exception while requesting quizzes, return zero quizzes: {}", ex.getMessage());
            return List.of();
        }
    }
}
