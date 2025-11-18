package com.htp.microservices.core.chapter.services;

import com.htp.microservices.api.core.chapter.Chapter;
import com.htp.microservices.api.core.chapter.ChapterService;
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
@RequestMapping("/api/v1/chapters")
public class ChapterServiceImpl implements ChapterService {

    private final ServiceUtil serviceUtil;
    private final ConcurrentHashMap<Long, Chapter> chapters = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        var serviceAddress = serviceUtil.getServiceAddress();
        chapters.put(101L, new Chapter(101L, 1L,
                "Chapter 1: Project Setup with Spring Initializr",
                "This chapter guides you through creating a new Spring Boot project using the **Spring Initializr** and selecting necessary dependencies like Spring Web.",
                serviceAddress));
        chapters.put(102L, new Chapter(102L, 1L,
                "Chapter 2: Creating Your First Controller",
                "Learn to define a basic **RestController** and implement a simple GET endpoint using the `@GetMapping` annotation to return a 'Hello World' message.",
                serviceAddress));
        chapters.put(201L, new Chapter(201L, 2L,
                "Chapter 1: Understanding Threads and Runnable",
                "An in-depth look at the fundamental concepts of Java threads, the `Runnable` interface, and how to start and manage basic thread lifecycles.",
                serviceAddress));
        chapters.put(202L, new Chapter(202L, 2L,
                "Chapter 2: The Executor Framework",
                "Explore the `java.util.concurrent.Executor` framework, specifically focusing on `ThreadPoolExecutor` and the benefits of using managed thread pools.",
                serviceAddress));

        log.info("Initialized {} chapters in the in-memory map.", chapters.size());
    }

    @Override
    public List<Chapter> getChaptersByCourseId(Long courseId) {
        if (courseId == null || courseId < 1) {
            log.error("Invalid courseId received for chapter lookup: {}", courseId);
            throw new InvalidRequestException("Invalid courseId (must be > 0): " + courseId);
        }

        List<Chapter> chaptersInCourse = chapters.values().stream()
                .filter(c -> courseId.equals(c.courseId()))
                .toList();

        log.debug("Found {} chapters for course ID: {}", chaptersInCourse.size(), courseId);
        return chaptersInCourse;
    }
}
