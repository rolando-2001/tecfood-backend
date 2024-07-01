package com.backend.app.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    @Getter
    private final HttpStatus status;
    private final String message;

    private CustomException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public static CustomException badRequest(String message) {
        return new CustomException(HttpStatus.BAD_REQUEST, message);
    }

    public static CustomException notFound(String message) {
        return new CustomException(HttpStatus.NOT_FOUND, message);
    }

    public static CustomException internalServerError(String message) {
        return new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
