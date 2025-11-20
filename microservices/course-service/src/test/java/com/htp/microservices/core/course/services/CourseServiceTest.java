package com.htp.microservices.core.course.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class CourseServiceTest {

    private static final String COURSE_ENDPOINT = "/api/v1/courses/";

    @Autowired
    private WebTestClient client;

    @Test
    void getCourseById() {
        var courseId = 1L;
        client.get()
                .uri(COURSE_ENDPOINT + courseId)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.courseId").isEqualTo(courseId);
    }

    @Test
    void getCourseInvalidParameterString() {
        var uri = COURSE_ENDPOINT + UUID.randomUUID();
        client.get()
                .uri(uri)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(INTERNAL_SERVER_ERROR)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo(uri)
                .jsonPath("$.message.bannerMessage").value(v -> assertTrue(v.toString()
                        .contains("Type mismatch.")));
    }

    @Test
    void getCourseNotFound() {
        var courseId = 123L;
        var uri = COURSE_ENDPOINT + courseId;
        client.get()
                .uri(uri)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo(uri)
                .jsonPath("$.message.bannerMessage").isEqualTo("Course not found for ID: " + courseId);
    }

    @Test
    void getCourseInvalidParameterNegativeValue() {
        var courseId = -1;
        var uri = COURSE_ENDPOINT + courseId;

        client.get()
                .uri(uri)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(UNPROCESSABLE_ENTITY)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo(uri)
                .jsonPath("$.message.bannerMessage").isEqualTo("Invalid courseId (must be > 0): " + courseId);
    }
}