package com.htp.microservices.util.response;

import com.htp.microservices.util.error.ErrorMessage;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.time.ZonedDateTime;
import java.util.Objects;

@Builder
public record ErrorResponse(
        String path,
        ErrorMessage message,
        HttpStatus httpStatus,
        ZonedDateTime timestamp
) {

    public ErrorResponse {
        Objects.requireNonNull(path, "path cannot be null");
        Objects.requireNonNull(message, "message cannot be null");
        Objects.requireNonNull(httpStatus, "httpStatus cannot be null");
        Objects.requireNonNull(timestamp, "timestamp cannot be null");
    }

    public static ErrorResponse fromException(HttpStatus httpStatus, ServerHttpRequest request, Exception ex) {
        String bannerMessage = (ex != null && ex.getMessage() != null)
                ? ex.getMessage()
                : httpStatus.getReasonPhrase();

        var errorMessage = ErrorMessage.builder()
                .bannerMessage(bannerMessage)
                .build();

        return createBuilder(httpStatus, request)
                .message(errorMessage)
                .build();
    }

    public static ErrorResponse fromMessage(HttpStatus httpStatus, ServerHttpRequest request, ErrorMessage message) {
        return createBuilder(httpStatus, request)
                .message(message)
                .build();
    }

    private static ErrorResponseBuilder createBuilder(HttpStatus httpStatus, ServerHttpRequest request) {
        return ErrorResponse.builder()
                .httpStatus(httpStatus)
                .timestamp(ZonedDateTime.now())
                .path(request.getPath().pathWithinApplication().value());
    }
}

