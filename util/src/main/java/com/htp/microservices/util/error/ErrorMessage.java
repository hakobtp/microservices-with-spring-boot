package com.htp.microservices.util.error;

import lombok.Builder;
import lombok.Singular;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Builder
public record ErrorMessage(
        String bannerMessage,
        @Singular List<FieldErrorInfo> fieldErrors
) {

    public ErrorMessage {
        Objects.requireNonNull(bannerMessage);
        fieldErrors = (fieldErrors == null) ? List.of() : List.copyOf(fieldErrors);
    }

    /**
     * Find a field error by its field name.
     */
    public Optional<FieldErrorInfo> findByFieldName(String fieldName) {
        return fieldErrors.stream()
                .filter(error -> Objects.equals(error.fieldName(), fieldName))
                .findFirst();
    }
}