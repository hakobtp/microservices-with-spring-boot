package com.htp.microservices.api.core.course;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/courses")
public interface CourseService {

    /**
     * Get a course by its ID
     * Example: GET /api/v1/courses/{courseId}
     */
    @GetMapping("/{courseId}")
    ResponseEntity<Course> getCourseById(@PathVariable Long courseId);

    /**
     * Get all courses
     * Example: GET /api/v1/courses
     */
    @GetMapping
    ResponseEntity<List<Course>> getAllCourses();

    /**
     * Create a new course
     * Example: POST /api/v1/courses
     */
    @PostMapping
    ResponseEntity<Course> createCourse(@RequestBody Course course);

    /**
     * Update an existing course
     * Example: PUT /api/v1/courses/{courseId}
     */
    @PutMapping("/{courseId}")
    ResponseEntity<Course> updateCourse(@PathVariable Long courseId, @RequestBody Course course);

    /**
     * Delete a course by its ID
     * Example: DELETE /api/v1/courses/{courseId}
     */
    @DeleteMapping("/{courseId}")
    ResponseEntity<Void> deleteCourse(@PathVariable Long courseId);
}