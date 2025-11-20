package com.htp.microservices.core.course_composite.services;

import com.htp.microservices.api.composite.models.CourseAggregate;
import com.htp.microservices.api.composite.services.CourseCompositeService;
import com.htp.microservices.api.core.chapter.Chapter;
import com.htp.microservices.api.core.quiz.Quiz;
import com.htp.microservices.api.exceptions.NotFoundException;
import com.htp.microservices.core.course_composite.mapper.ChapterMapper;
import com.htp.microservices.core.course_composite.mapper.CourseMapper;
import com.htp.microservices.core.course_composite.mapper.ServiceAddressesMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/course-composite")
public class CourseCompositeServiceImpl implements CourseCompositeService {

    private final CourseMapper courseMapper;
    private final ChapterMapper chapterMapper;
    private final ServiceAddressesMapper serviceAddressesMapper;
    private final CourseCompositeIntegrationService integrationService;

    @Override
    public CourseAggregate getCourse(Long courseId) {

        log.info("Fetching composite course structure for courseId={}", courseId);

        var course = integrationService.getCourseById(courseId);
        if (course == null) {
            log.warn("Course not found: {}", courseId);
            throw new NotFoundException("Course not found with id: " + courseId);
        }

        var chapters = integrationService.getChaptersByCourseId(course.courseId());
        log.debug("Fetched {} chapters for course {}", chapters.size(), courseId);


        Map<Long, List<Quiz>> quizMap = chapters.stream()
                .collect(Collectors.toMap(
                        Chapter::chapterId,
                        chapter -> {
                            var chapterId = chapter.chapterId();
                            var quizzes = integrationService.getQuizzesByChapterId(chapterId);
                            log.debug("Found {} quizzes for chapter {}", quizzes.size(), chapterId);
                            return quizzes;
                        }
                ));

        var allQuizzes = quizMap.values().stream().flatMap(List::stream).toList();


        var addresses = serviceAddressesMapper.toAddresses(course, chapters, allQuizzes);
        var chapterSummaries = chapterMapper.toChapterSummaries(chapters, quizMap);
        return courseMapper.toCourseAggregate(course, addresses, chapterSummaries);
    }
}