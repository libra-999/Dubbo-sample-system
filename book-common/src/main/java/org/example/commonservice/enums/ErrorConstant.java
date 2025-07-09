package org.example.commonservice.enums;

import lombok.Getter;

@Getter
public enum ErrorConstant {
    ERROR("An unexpected error occurred"),
    UNKNOWN_ERROR("Unknown error occurred"),
    VALIDATION_ERROR("Validation error"),
    UNAUTHORIZED("Authentication failed");

    private final String message;

    ErrorConstant(String message) {
        this.message = message;
    }
}
