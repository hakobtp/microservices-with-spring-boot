package com.htp.microservices.core.quiz.services;

import com.htp.microservices.api.core.quiz.Quiz;
import com.htp.microservices.api.core.quiz.QuizService;
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
public class QuizServiceImpl implements QuizService {

    private static final Random RANDOM = new Random();
    private final ServiceUtil serviceUtil;

    @Override
    public ResponseEntity<Quiz> getQuizById(Long quizId) {
        log.debug("/quizzes returning quiz for quizId={}", quizId);

        if (quizId < 1) {
            throw new InvalidRequestException("Invalid quizId (must be > 0): " + quizId);
        }

        if (quizId == 40) {
            throw new NotFoundException("No quiz found for quizId: " + quizId);
        }

        Long chapterId = RANDOM.nextLong(1000) + 100;
        Quiz quiz = new Quiz(
                quizId,
                chapterId,
                "Quiz " + quizId,
                "Correct answer for quiz " + quizId,
                serviceUtil.getServiceAddress(),
                List.of("Option A", "Option B", "Option C", "Option D")
        );

        return ResponseEntity.ok(quiz);
    }

    @Override
    public ResponseEntity<List<Quiz>> getQuizzesByChapterId(Long chapterId) {
        log.debug("/quizzes returning quizzes for chapterId={}", chapterId);

        if (chapterId < 1) {
            throw new InvalidRequestException("Invalid chapterId (must be > 0): " + chapterId);
        }

        List<Quiz> quizzes = List.of(
                new Quiz(1L, chapterId, "Java Basics Quiz", "Correct answer for Java Basics", serviceUtil.getServiceAddress(),
                        List.of("Option A", "Option B", "Option C", "Option D")),
                new Quiz(2L, chapterId, "Spring Boot Quiz", "Correct answer for Spring Boot", serviceUtil.getServiceAddress(),
                        List.of("Option A", "Option B", "Option C", "Option D")),
                new Quiz(3L, chapterId, "Microservices Quiz", "Correct answer for Microservices", serviceUtil.getServiceAddress(),
                        List.of("Option A", "Option B", "Option C", "Option D"))
        );
        return ResponseEntity.ok(quizzes);
    }

    @Override
    public ResponseEntity<Quiz> createQuiz(Quiz quiz) {
        log.debug("Creating new quiz (stubbed)");
        Long newId = RANDOM.nextLong(1000) + 100;
        Quiz newQuiz = new Quiz(newId, quiz.chapterId(),
                quiz.question(), quiz.correctAnswer(),
                serviceUtil.getServiceAddress(), quiz.answerOptions());
        log.info("Created new quiz with stubbed id {}", newId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newQuiz);
    }

    @Override
    public ResponseEntity<Quiz> updateQuiz(Long quizId, Quiz quiz) {
        log.debug("Updating quiz with id {} (stubbed)", quizId);

        if (quizId < 1) {
            throw new InvalidRequestException("Invalid quizId: " + quizId);
        }
        if (quizId == 40) {
            throw new NotFoundException("No quiz found to update for quizId: " + quizId);
        }

        var updatedQuiz = new Quiz(quizId, quiz.chapterId(),
                quiz.question(), quiz.correctAnswer(),
                serviceUtil.getServiceAddress(), quiz.answerOptions());
        return ResponseEntity.ok(updatedQuiz);
    }

    @Override
    public ResponseEntity<Void> deleteQuiz(Long quizId) {
        log.debug("Deleting quiz with id {} (stubbed)", quizId);

        if (quizId < 1) {
            throw new InvalidRequestException("Invalid quizId: " + quizId);
        }
        if (quizId == 40) {
            throw new NotFoundException("No quiz found to delete for quizId: " + quizId);
        }

        log.info("Successfully 'deleted' quiz with id {}", quizId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
