package com.htp.microservices.core.course.services;

import com.htp.microservices.api.core.course.Course;
import com.htp.microservices.api.core.course.CourseDifficultyLevel;
import com.htp.microservices.api.core.course.CourseService;
import com.htp.microservices.api.exceptions.InvalidRequestException;
import com.htp.microservices.api.exceptions.NotFoundException;
import com.htp.microservices.util.service.ServiceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final ServiceUtil serviceUtil;

    @Override
    public ResponseEntity<Course> getCourseById(Long courseId) {
        log.debug("/courses returning course for courseId={}", courseId);

        if (courseId < 1) {
            throw new InvalidRequestException("Invalid courseId (must be > 0): " + courseId);
        }

        if (courseId == 40) {
            throw new NotFoundException("No course found for courseId: " + courseId);
        }

        var course = new Course(
                courseId,
                "name-" + courseId,
                "desc" + courseId,
                serviceUtil.getServiceAddress(),
                CourseDifficultyLevel.EASY);

        return ResponseEntity.ok(course);
    }

    @Override
    public ResponseEntity<List<Course>> getAllCourses() {
        log.debug("Returning all courses (stubbed)");
        var serviceAddress = serviceUtil.getServiceAddress();
        var courses = List.of(
                new Course(1L, "Java Basics",
                        "Learn the fundamentals of Java.", serviceAddress, CourseDifficultyLevel.MEDIUM),
                new Course(2L, "Spring Boot Microservices",
                        "Building distributed systems with Spring Boot.", serviceAddress, CourseDifficultyLevel.HARD)
        );
        return ResponseEntity.ok(courses);
    }

    @Override
    public ResponseEntity<Course> createCourse(Course course) {
        log.debug("Creating new course (stubbed)");
        Long newId = new Random().nextLong(1000) + 100;
        Course newCourse = new Course(
                newId,
                course.title(),
                course.description(),
                serviceUtil.getServiceAddress(),
                CourseDifficultyLevel.HARD
        );
        log.info("Created new course with stubbed id {}", newId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCourse);
    }

    @Override
    public ResponseEntity<Course> updateCourse(Long courseId, Course course) {
        log.debug("Updating course with id {} (stubbed)", courseId);
        if (courseId < 1) {
            throw new InvalidRequestException("Invalid courseId: " + courseId);
        }
        if (courseId == 40) {
            throw new NotFoundException("No course found to update for courseId: " + courseId);
        }
        Course updatedCourse = new Course(
                courseId,
                course.title(),
                course.description(),
                serviceUtil.getServiceAddress(),
                CourseDifficultyLevel.HARD
        );
        return ResponseEntity.ok(updatedCourse);
    }

    @Override
    public ResponseEntity<Void> deleteCourse(Long courseId) {
        log.debug("Deleting course with id {} (stubbed)", courseId);

        if (courseId < 1) {
            throw new InvalidRequestException("Invalid courseId: " + courseId);
        }
        if (courseId == 40) {
            throw new NotFoundException("No course found to delete for courseId: " + courseId);
        }

        log.info("Successfully 'deleted' course with id {}", courseId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
