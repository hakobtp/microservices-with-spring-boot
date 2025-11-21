package com.htp.microservices.core.quiz.services;

import com.htp.microservices.api.core.quiz.Quiz;
import com.htp.microservices.api.core.quiz.QuizService;
import com.htp.microservices.api.exceptions.InvalidRequestException;
import com.htp.microservices.util.service.ServiceUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/quizzes")
public class QuizServiceImpl implements QuizService {

    private final ServiceUtil serviceUtil;
    private final ConcurrentHashMap<Long, Quiz> quizzes = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        var serviceAddress = serviceUtil.getServiceAddress();
        quizzes.put(1L, new Quiz(1L, 101L,
                "What is the primary web interface used to bootstrap a Spring Boot project?",
                "Spring Initializr",
                serviceAddress,
                List.of("Maven Central", "Spring Initializr", "Spring Boot CLI", "Apache Maven")));
        quizzes.put(2L, new Quiz(2L, 101L,
                "Which dependency is essential for creating RESTful APIs in Spring Boot?",
                "Spring Web",
                serviceAddress,
                List.of("Spring Data JPA", "Spring Security", "Spring Web", "Spring DevTools")));
        quizzes.put(3L, new Quiz(3L, 102L,
                "Which Spring annotation marks a class as a controller where every method returns a domain object instead of a view?",
                "@RestController",
                serviceAddress,
                List.of("@Controller", "@Service", "@Repository", "@RestController")));
        quizzes.put(4L, new Quiz(4L, 201L,
                "Which interface is commonly used to define a task that can be executed by a thread?",
                "Runnable",
                serviceAddress,
                List.of("Callable", "Executable", "Runnable", "Future")));
        quizzes.put(5L, new Quiz(5L, 201L,
                "What method is called to start the execution of a new thread?",
                "start()",
                serviceAddress,
                List.of("run()", "execute()", "init()", "start()")));

        log.info("Initialized {} quizzes in the in-memory map.", quizzes.size());
    }

    @Override
    public List<Quiz> getQuizzesByChapterId(Long chapterId) {
        if (chapterId == null || chapterId < 1) {
            log.error("Invalid chapterId received: {}", chapterId);
            throw new InvalidRequestException("Invalid chapterId (must be > 0): " + chapterId);
        }
        List<Quiz> quizzesForChapter = quizzes.values().stream()
                .filter(quiz -> chapterId.equals(quiz.chapterId()))
                .toList();

        if (quizzesForChapter.isEmpty()) {
            log.warn("No quizzes found for chapter ID: {}", chapterId);
        } else {
            log.debug("Found {} quizzes for chapter ID: {}", quizzesForChapter.size(), chapterId);
        }

        return quizzesForChapter;
    }
}
