package ru.yandex.practicum.filmorate.Validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmValidatorTest {
    private FilmValidator validator;

    @BeforeEach
    void beforeAll() {
        validator = new FilmValidator();
    }

    @Test
    void shouldReturnValidationExceptionIfDateReleaseBefore18951228() {
        Film film = new Film("Боевик", LocalDate.of(1800, 02, 03), Duration.ofDays(100), "Интересный");
        final ValidationException exception = assertThrows(ValidationException.class,
                () -> validator.validate(film));
    }

    @Test
    void shouldReturnValidationExceptionIfDurationLessZero() {
        Film film = new Film("Боевик", LocalDate.of(1800, 02, 03), Duration.ofMinutes(-100), "Интересный");
        final ValidationException exception = assertThrows(ValidationException.class,
                () -> validator.validate(film));
    }

}