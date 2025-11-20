package com.htp.microservices.core.course_composite.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.htp.microservices.util.exceptions.CourseCompositeException;
import com.htp.microservices.util.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@Service
@RequiredArgsConstructor
public class HttpClientErrorHandleService {

    private final ObjectMapper objectMapper;

    public void translateHttpClientError(HttpClientErrorException ex) {
        var statusCode = ex.getStatusCode();
        var body = ex.getResponseBodyAsString();
        var resolved = HttpStatus.resolve(statusCode.value());
        if (resolved == null) {
            log.error("Received unknown HTTP status: {}. Body: {}", statusCode, body);
            throw ex;
        }

        if (resolved.is5xxServerError()) {
            log.error("Received server error ({}). Body: {}", statusCode, body);
            throw ex;
        }

        try {
            var errorResponse = objectMapper.readValue(body, ErrorResponse.class);
            log.debug(
                    "Client error ({}). Path: {}. Message: {}",
                    resolved,
                    errorResponse.path(),
                    errorResponse.message() != null ? errorResponse.message().bannerMessage() : "No message"
            );

            throw new CourseCompositeException(errorResponse);

        } catch (JsonProcessingException jsonEx) {
            log.error("Failed to parse error response JSON for status {}. Raw body: {}", statusCode, body);
            throw new RuntimeException("Failed to parse error response from downstream service.", jsonEx);
        }
    }
}
