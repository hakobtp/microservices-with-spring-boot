package com.htp.microservices.api.core.chapter;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ChapterService {

    @GetMapping
    List<Chapter> getChaptersByCourseId(@RequestParam(value = "courseId") Long courseId);
}