package com.htp.microservices.core.chapter.services;

import com.htp.microservices.api.core.chapter.Chapter;
import com.htp.microservices.api.core.chapter.ChapterService;
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
public class ChapterServiceImpl implements ChapterService {

    private static final Random RANDOM = new Random();

    private final ServiceUtil serviceUtil;

    @Override
    public ResponseEntity<Chapter> getChapterById(Long chapterId) {
        log.debug("/chapters returning chapter for chapterId={}", chapterId);

        if (chapterId < 1) {
            throw new InvalidRequestException("Invalid chapterId (must be > 0): " + chapterId);
        }

        if (chapterId == 40) {
            throw new NotFoundException("No chapter found for chapterId: " + chapterId);
        }

        Long courseId = RANDOM.nextLong(1000) + 100;
        var chapter = new Chapter(
                chapterId,
                courseId,
                "Chapter " + chapterId,
                "Description for chapter " + chapterId,
                serviceUtil.getServiceAddress());
        return ResponseEntity.ok(chapter);
    }

    @Override
    public ResponseEntity<List<Chapter>> getChaptersByCourseId(Long courseId) {
        log.debug("/chapters returning chapters for courseId={}", courseId);

        if (courseId < 1) {
            throw new InvalidRequestException("Invalid courseId (must be > 0): " + courseId);
        }

        var chapters = List.of(
                new Chapter(1L, courseId, "Introduction", "Basics of the course", serviceUtil.getServiceAddress()),
                new Chapter(2L, courseId, "Deep Dive", "In-depth content about the course", serviceUtil.getServiceAddress()),
                new Chapter(3L, courseId, "Summary", "Course wrap-up and final thoughts", serviceUtil.getServiceAddress())
        );
        return ResponseEntity.ok(chapters);
    }

    @Override
    public ResponseEntity<Chapter> createChapter(Chapter chapter) {
        log.debug("Creating new chapter (stubbed)");

        var newId = RANDOM.nextLong(1000) + 100;
        Chapter newChapter = new Chapter(
                newId,
                chapter.courseId(),
                chapter.title(),
                chapter.content(),
                serviceUtil.getServiceAddress());

        log.info("Created new chapter with stubbed id {}", newId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newChapter);
    }

    @Override
    public ResponseEntity<Chapter> updateChapter(Long chapterId, Chapter chapter) {
        log.debug("Updating chapter with id {} (stubbed)", chapterId);

        if (chapterId < 1) {
            throw new InvalidRequestException("Invalid chapterId: " + chapterId);
        }
        if (chapterId == 40) {
            throw new NotFoundException("No chapter found to update for chapterId: " + chapterId);
        }

        var updatedChapter = new Chapter(chapterId, chapter.courseId(),
                chapter.title(), chapter.content(), serviceUtil.getServiceAddress());
        return ResponseEntity.ok(updatedChapter);
    }

    @Override
    public ResponseEntity<Void> deleteChapter(Long chapterId) {
        log.debug("Deleting chapter with id {} (stubbed)", chapterId);

        if (chapterId < 1) {
            throw new InvalidRequestException("Invalid chapterId: " + chapterId);
        }
        if (chapterId == 40) {
            throw new NotFoundException("No chapter found to delete for chapterId: " + chapterId);
        }

        log.info("Successfully 'deleted' chapter with id {}", chapterId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
