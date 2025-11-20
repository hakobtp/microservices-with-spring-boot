package com.htp.microservices.core.course_composite.mapper;

import com.htp.microservices.api.composite.models.ServiceAddresses;
import com.htp.microservices.api.core.chapter.Chapter;
import com.htp.microservices.api.core.course.Course;
import com.htp.microservices.api.core.quiz.Quiz;
import com.htp.microservices.core.course_composite.util.ServiceAddressResolverUtil;
import com.htp.microservices.util.service.ServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ServiceAddressesMapper {

    private final ServiceUtil serviceUtil;

    public ServiceAddresses toAddresses(Course course, List<Chapter> chapters, List<Quiz> quizzes) {
        var courseAddress = ServiceAddressResolverUtil.resolveCourseAddress(course);
        var chapterAddress = ServiceAddressResolverUtil.resolveChapterAddress(chapters);
        var quizAddress = ServiceAddressResolverUtil.resolveQuizAddress(quizzes);

        return new ServiceAddresses(
                courseAddress,
                chapterAddress,
                quizAddress,
                serviceUtil.getServiceAddress()
        );
    }
}
