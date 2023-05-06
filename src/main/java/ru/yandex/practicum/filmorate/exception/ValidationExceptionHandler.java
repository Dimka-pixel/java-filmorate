package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;


@ControllerAdvice
public class ValidationExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handleConflict(ValidationException ex, WebRequest request) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(Map.of("errorMessage", ex.getErrorMessage()));
    }


}
