package com.htp.microservices.core.course_composite.mapper;

import com.htp.microservices.api.composite.models.ChapterSummary;
import com.htp.microservices.api.composite.models.CourseAggregate;
import com.htp.microservices.api.composite.models.ServiceAddresses;
import com.htp.microservices.api.core.course.Course;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseMapper {

    public CourseAggregate toCourseAggregate(
            Course course,
            ServiceAddresses serviceAddresses,
            List<ChapterSummary> chapters
    ) {
        return new CourseAggregate(
                course.courseId(),
                course.title(),
                course.description(),
                course.difficultyLevel(),
                serviceAddresses,
                chapters
        );
    }
}
