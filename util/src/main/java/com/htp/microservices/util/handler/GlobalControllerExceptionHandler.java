package com.htp.microservices.util.handler;

import com.htp.microservices.api.exceptions.InvalidRequestException;
import com.htp.microservices.api.exceptions.NotFoundException;
import com.htp.microservices.util.error.ErrorMessage;
import com.htp.microservices.util.error.FieldErrorInfo;
import com.htp.microservices.util.exceptions.CourseCompositeException;
import com.htp.microservices.util.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public @ResponseBody ErrorResponse handleNotFoundExceptions(
            ServerHttpRequest request,
            NotFoundException exception
    ) {
        return createHttpErrorResponse(NOT_FOUND, request, exception);
    }

    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidRequestException.class)
    public @ResponseBody ErrorResponse handleInvalidInputException(
            ServerHttpRequest request,
            InvalidRequestException exception
    ) {
        return createHttpErrorResponse(UNPROCESSABLE_ENTITY, request, exception);
    }

    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(WebExchangeBindException.class)
    public ErrorResponse handleValidationException(ServerHttpRequest request, WebExchangeBindException exception) {
        var fieldErrors = exception.getBindingResult().getFieldErrors().stream()
                .map(FieldErrorInfo::from)
                .toList();

        ErrorMessage errorMessage = ErrorMessage.builder()
                .bannerMessage("Request contains invalid data. Please check field errors.")
                .fieldErrors(fieldErrors)
                .build();

        var errorResponse = ErrorResponse.fromMessage(UNPROCESSABLE_ENTITY, request, errorMessage);

        log.debug("Returning HTTP status: {} for path: {}, message: {}. Field errors: {}",
                errorResponse.httpStatus(), errorResponse.path(),
                errorResponse.message().bannerMessage(), fieldErrors);

        return errorResponse;
    }

    @ExceptionHandler(CourseCompositeException.class)
    public ResponseEntity<ErrorResponse> handleCourseCompositeException(CourseCompositeException exception) {
        var errorResponse = exception.getErrorResponse();
        return ResponseEntity.status(errorResponse.httpStatus())
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(ServerHttpRequest request, Exception exception) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (exception instanceof ResponseStatusException) {
            var statusCode = ((ResponseStatusException) exception).getBody().getStatus();
            HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
            if (httpStatus.is4xxClientError()) {
                status = httpStatus;
            }
        }
        return ResponseEntity.status(status)
                .body(createHttpErrorResponse(status, request, exception));
    }

    private ErrorResponse createHttpErrorResponse(
            HttpStatus status,
            ServerHttpRequest request,
            Exception exception
    ) {
        var er = ErrorResponse.fromException(status, request, exception);
        if (status.is5xxServerError()) {
            log.error("Returning HTTP status: {} for path: {}. Message: {}",
                    er.httpStatus(), er.path(), er.message().bannerMessage(), exception);
        } else {
            log.debug("Returning HTTP status: {} for path: {}. Message: {}",
                    er.httpStatus(), er.path(), er.message().bannerMessage());
        }
        return er;
    }
}
