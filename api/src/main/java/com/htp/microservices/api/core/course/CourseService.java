package com.htp.microservices.api.core.course;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface CourseService {

    @GetMapping("/{courseId}")
    Course getCourseById(@PathVariable Long courseId);
}