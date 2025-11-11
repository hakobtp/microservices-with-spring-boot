package com.htp.microservices.api.core.chapter;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/chapters")
public interface ChapterService {

    /**
     * Get a chapter by its ID
     * Example: GET /api/v1/chapters/{chapterId}
     */
    @GetMapping("/{chapterId}")
    ResponseEntity<Chapter> getChapterById(@PathVariable Long chapterId);

    /**
     * Get all chapters for a specific course
     * Example: GET /api/v1/chapters/course/{courseId}
     */
    @GetMapping("/course/{courseId}")
    ResponseEntity<List<Chapter>> getChaptersByCourseId(@PathVariable Long courseId);

    /**
     * Create a new chapter
     * Example: POST /api/v1/chapters
     */
    @PostMapping
    ResponseEntity<Chapter> createChapter(@RequestBody Chapter chapter);

    /**
     * Update an existing chapter
     * Example: PUT /api/v1/chapters/{chapterId}
     */
    @PutMapping("/{chapterId}")
    ResponseEntity<Chapter> updateChapter(@PathVariable Long chapterId, @RequestBody Chapter chapter);

    /**
     * Delete a chapter by its ID
     * Example: DELETE /api/v1/chapters/{chapterId}
     */
    @DeleteMapping("/{chapterId}")
    ResponseEntity<Void> deleteChapter(@PathVariable Long chapterId);
}