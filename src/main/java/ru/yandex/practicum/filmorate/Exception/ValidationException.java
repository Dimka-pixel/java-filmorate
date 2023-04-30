package ru.yandex.practicum.filmorate.Exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends RuntimeException {
    private String errorMessage;
    private HttpStatus status;

    public ValidationException(String errorMessage, HttpStatus status) {
        this.errorMessage = errorMessage;
        this.status = status;
    }


    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
