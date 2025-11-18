package com.htp.microservices.api.core.quiz;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface QuizService {

    @GetMapping
    List<Quiz> getQuizzesByChapterId(@RequestParam(value = "chapterId") Long chapterId);
}