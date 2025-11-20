package com.htp.microservices.util.exceptions;

import com.htp.microservices.api.exceptions.BaseException;
import com.htp.microservices.util.response.ErrorResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class CourseCompositeException extends BaseException {

    private final ErrorResponse errorResponse;
}
