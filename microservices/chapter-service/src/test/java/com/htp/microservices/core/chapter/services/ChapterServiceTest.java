package com.htp.microservices.core.chapter.services;

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
class ChapterServiceTest {

    private static final String CHAPTER_ENDPOINT = "/api/v1/chapters";

    @Autowired
    private WebTestClient client;

    @Test
    void getChaptersByCourseId() {
        var courseId = 1L;
        Function<UriBuilder, URI> uriFunction = uriBuilder ->
                uriBuilder
                        .path(CHAPTER_ENDPOINT)
                        .queryParam("courseId", courseId)
                        .build();

        client.get()
                .uri(uriFunction)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[0].courseId").isEqualTo(courseId)
                .jsonPath("$[1].courseId").isEqualTo(courseId);
    }

    @Test
    void getChaptersByCourseIdMissingParameter() {
        client.get()
                .uri(CHAPTER_ENDPOINT)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo(CHAPTER_ENDPOINT)
                .jsonPath("$.message.bannerMessage")
                .isEqualTo("400 BAD_REQUEST \"Required query parameter 'courseId' is not present.\"");
    }

    @Test
    void getChaptersByCourseIdInvalidParameter() {
        Function<UriBuilder, URI> uriFunction = uriBuilder ->
                uriBuilder
                        .path(CHAPTER_ENDPOINT)
                        .queryParam("courseId", "courseId")
                        .build();

        client.get()
                .uri(uriFunction)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo(CHAPTER_ENDPOINT)
                .jsonPath("$.message.bannerMessage")
                .isEqualTo("400 BAD_REQUEST \"Type mismatch.\"");
    }

    @Test
    void getChaptersByCourseIdNotFound() {
        var courseId = 3345L;
        Function<UriBuilder, URI> uriFunction = uriBuilder ->
                uriBuilder
                        .path(CHAPTER_ENDPOINT)
                        .queryParam("courseId", courseId)
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
        var courseId = -1L;
        Function<UriBuilder, URI> uriFunction = uriBuilder ->
                uriBuilder
                        .path(CHAPTER_ENDPOINT)
                        .queryParam("courseId", courseId)
                        .build();
        client.get()
                .uri(uriFunction)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(UNPROCESSABLE_ENTITY)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo(CHAPTER_ENDPOINT)
                .jsonPath("$.message.bannerMessage").isEqualTo("Invalid courseId (must be > 0): -1");
    }

}