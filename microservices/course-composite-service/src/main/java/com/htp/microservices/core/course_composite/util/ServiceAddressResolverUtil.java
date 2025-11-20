package com.htp.microservices.core.course_composite.util;

import com.htp.microservices.api.core.chapter.Chapter;
import com.htp.microservices.api.core.course.Course;
import com.htp.microservices.api.core.quiz.Quiz;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

public interface ServiceAddressResolverUtil {

    static String resolveCourseAddress(Course course) {
        if (course == null) {
            return "";
        }
        var address = course.serviceAddress();
        return address != null ? address : "";
    }

    static String resolveChapterAddress(List<Chapter> chapters) {
        if (CollectionUtils.isEmpty(chapters)) {
            return "";
        }
        return chapters.stream()
                .map(Chapter::serviceAddress)
                .filter(StringUtils::hasLength)
                .findAny()
                .orElse("");
    }

    static String resolveQuizAddress(List<Quiz> quizzes) {
        if (CollectionUtils.isEmpty(quizzes)) {
            return "";
        }
        return quizzes.stream()
                .map(Quiz::serviceAddress)
                .filter(StringUtils::hasLength)
                .findAny()
                .orElse("");
    }
}
