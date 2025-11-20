package com.htp.microservices.api.composite.services;

import com.htp.microservices.api.composite.models.CourseAggregate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface CourseCompositeService {

    @GetMapping("/{courseId}")
    CourseAggregate getCourse(@PathVariable Long courseId);
}
