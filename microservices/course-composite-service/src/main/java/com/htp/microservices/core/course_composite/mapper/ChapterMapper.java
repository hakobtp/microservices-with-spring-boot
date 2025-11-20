package com.htp.microservices.core.course_composite.mapper;

import com.htp.microservices.api.composite.models.ChapterSummary;
import com.htp.microservices.api.core.chapter.Chapter;
import com.htp.microservices.api.core.quiz.Quiz;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ChapterMapper {

    private final QuizMapper quizMapper;

    public List<ChapterSummary> toChapterSummaries(
            List<Chapter> chapters,
            Map<Long, List<Quiz>> quizMap
    ) {
        return chapters.stream()
                .map(ch -> new ChapterSummary(
                        ch.chapterId(),
                        ch.title(),
                        ch.content(),
                        quizMapper.toQuizSummaries(quizMap.getOrDefault(ch.chapterId(), List.of()))
                )).toList();
    }
}
