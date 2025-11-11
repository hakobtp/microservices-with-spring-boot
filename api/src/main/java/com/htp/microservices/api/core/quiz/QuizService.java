package com.htp.microservices.api.core.quiz;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/quizzes")
public interface QuizService {

    /**
     * Get a quiz by its ID
     * Example: GET /api/v1/quizzes/{quizId}
     */
    @GetMapping("/{quizId}")
    ResponseEntity<Quiz> getQuizById(@PathVariable Long quizId);

    /**
     * Get all quizzes for a specific course
     * Example: GET /api/v1/quizzes/course/{courseId}
     */
    @GetMapping("/course/{courseId}")
    ResponseEntity<List<Quiz>> getQuizzesByChapterId(@PathVariable Long courseId);

    /**
     * Create a new quiz
     * Example: POST /api/v1/quizzes
     */
    @PostMapping
    ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz);

    /**
     * Update an existing quiz
     * Example: PUT /api/v1/quizzes/{quizId}
     */
    @PutMapping("/{quizId}")
    ResponseEntity<Quiz> updateQuiz(@PathVariable Long quizId, @RequestBody Quiz quiz);

    /**
     * Delete a quiz by its ID
     * Example: DELETE /api/v1/quizzes/{quizId}
     */
    @DeleteMapping("/{quizId}")
    ResponseEntity<Void> deleteQuiz(@PathVariable Long quizId);
}