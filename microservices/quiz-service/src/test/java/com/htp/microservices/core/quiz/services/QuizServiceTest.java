package com.htp.microservices.core.quiz.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.function.Function;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class QuizServiceTest {

    private static final String QUIZ_ENDPOINT = "/api/v1/quizzes";

    @Autowired
    private WebTestClient client;

    @Test
    void getQuizzesByChapterId() {
        var chapterId = 101L;
        Function<UriBuilder, URI> uriFunction = uriBuilder ->
                uriBuilder
                        .path(QUIZ_ENDPOINT)
                        .queryParam("chapterId", chapterId)
                        .build();

        client.get()
                .uri(uriFunction)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[0].chapterId").isEqualTo(chapterId)
                .jsonPath("$[1].chapterId").isEqualTo(chapterId);
    }

    @Test
    void getQuizzesByChapterIdMissingParameter() {
        client.get()
                .uri(QUIZ_ENDPOINT)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo(QUIZ_ENDPOINT)
                .jsonPath("$.message.bannerMessage")
                .isEqualTo("400 BAD_REQUEST \"Required query parameter 'chapterId' is not present.\"");
    }

    @Test
    void getQuizzesByChapterIdInvalidParameter() {
        Function<UriBuilder, URI> uriFunction = uriBuilder ->
                uriBuilder
                        .path(QUIZ_ENDPOINT)
                        .queryParam("chapterId", "chapterId")
                        .build();

        client.get()
                .uri(uriFunction)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo(QUIZ_ENDPOINT)
                .jsonPath("$.message.bannerMessage")
                .isEqualTo("400 BAD_REQUEST \"Type mismatch.\"");
    }

    @Test
    void getChaptersByCourseIdNotFound() {
        var chapterId = 3345L;
        Function<UriBuilder, URI> uriFunction = uriBuilder ->
                uriBuilder
                        .path(QUIZ_ENDPOINT)
                        .queryParam("chapterId", chapterId)
                        .build();
        client.get()
                .uri(uriFunction)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(0);
    }

    @Test
    void getChaptersByCourseIdInvalidParameterNegativeValue() {
        var chapterId = -1L;
        Function<UriBuilder, URI> uriFunction = uriBuilder ->
                uriBuilder
                        .path(QUIZ_ENDPOINT)
                        .queryParam("chapterId", chapterId)
                        .build();
        client.get()
                .uri(uriFunction)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(UNPROCESSABLE_ENTITY)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo(QUIZ_ENDPOINT)
                .jsonPath("$.message.bannerMessage").isEqualTo("Invalid chapterId (must be > 0): -1");
    }
}