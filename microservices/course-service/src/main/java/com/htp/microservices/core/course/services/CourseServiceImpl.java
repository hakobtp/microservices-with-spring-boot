package com.htp.microservices.core.course.services;

import com.htp.microservices.api.core.course.Course;
import com.htp.microservices.api.core.course.CourseDifficultyLevel;
import com.htp.microservices.api.core.course.CourseService;
import com.htp.microservices.api.exceptions.InvalidRequestException;
import com.htp.microservices.api.exceptions.NotFoundException;
import com.htp.microservices.util.service.ServiceUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
public class CourseServiceImpl implements CourseService {


    private final ServiceUtil serviceUtil;
    private final ConcurrentHashMap<Long, Course> courses = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        var serviceAddress = serviceUtil.getServiceAddress();

        courses.put(1L, new Course(1L,
                "Introduction to Spring Boot",
                "Learn the basics of Spring Boot 3.",
                serviceAddress,
                CourseDifficultyLevel.MEDIUM));

        courses.put(2L, new Course(2L,
                "Advanced Java Concurrency",
                "Deep dive into concurrent programming in Java.",
                serviceAddress,
                CourseDifficultyLevel.HARD));

        log.info("Initialized {} courses in the in-memory map.", courses.size());
    }


    @Override
    public Course getCourseById(Long courseId) {
        if (courseId == null || courseId < 1) {
            log.error("Invalid courseId received: {}", courseId);
            throw new InvalidRequestException("Invalid courseId (must be > 0): " + courseId);
        }

        Course course = courses.get(courseId);
        if (course == null) {
            log.warn("Course not found for ID: {}", courseId);
            throw new NotFoundException("Course not found for ID: " + courseId);
        }

        log.debug("Found course with ID {}: {}", courseId, course.title());
        return course;
    }
}
