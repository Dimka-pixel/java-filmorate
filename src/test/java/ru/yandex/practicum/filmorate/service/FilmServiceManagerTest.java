package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmServiceManagerTest {
    private FilmServiceManager manager;

    @BeforeEach
    void beforeAll() {
        manager = new FilmServiceManager();
    }

    @Test
    void shouldCreateFilmIdAndSaveFilmInMap() {
        Film film = new Film("Боевик", LocalDate.of(1900, 2, 3), Duration.ofMinutes(100), "Интересный");
        manager.addFilm(film);

        assertEquals(1, film.getId());
        assertEquals(film, manager.getFilms().get(0));
    }

    @Test
    void shouldReturnValidationExceptionIfDateReleaseBefore18951228() {
        Film film = new Film("Боевик", LocalDate.of(1800, 2, 3), Duration.ofMinutes(100), "Интересный");
        final ValidationException exception = assertThrows(ValidationException.class,
                () -> manager.addFilm(film));
        assertEquals("дата релиза не может быть ранее 28 декабря 1895", exception.getErrorMessage());
    }

    @Test
    void shouldReturnValidationExceptionIfDurationLessZero() {
        Film film = new Film("Боевик", LocalDate.of(1900, 2, 3), Duration.ofMinutes(-100), "Интересный");
        final ValidationException exception = assertThrows(ValidationException.class,
                () -> manager.addFilm(film));
        assertEquals("Продолжительность не может быть отрицательной", exception.getErrorMessage());
    }

    @Test
    void shouldUpdateFilm() {
        Film film = new Film("Боевик", LocalDate.of(1900, 2, 3), Duration.ofMinutes(100), "Интересный");
        manager.addFilm(film);
        Film film2 = new Film("Update", LocalDate.of(1900, 2, 3), Duration.ofMinutes(100), "Интересный");
        film2.setId(1);
        manager.updateFilm(film2);

        assertEquals("Update", manager.getFilms().get(0).getName());
    }

    @Test
    void shouldReturnValidationExceptionForFilmUpdateIfMapIsEmpty() {
        Film film = new Film("Боевик", LocalDate.of(1800, 2, 3), Duration.ofMinutes(100), "Интересный");
        final ValidationException exception = assertThrows(ValidationException.class,
                () -> manager.updateFilm(film));
        assertEquals("фильм не найден", exception.getErrorMessage());
    }

    @Test
    void shouldReturnValidationExceptionForUpdateFilmIfDateReleaseBefore18951228() {
        Film film = new Film("Боевик", LocalDate.of(1800, 2, 3), Duration.ofMinutes(100), "Интересный");
        final ValidationException exception = assertThrows(ValidationException.class,
                () -> manager.updateFilm(film));
        assertEquals("дата релиза не может быть ранее 28 декабря 1895", exception.getErrorMessage());
    }

    @Test
    void shouldReturnValidationExceptionForUpdateFilmIfDurationLessZero() {
        Film film = new Film("Боевик", LocalDate.of(1900, 2, 3), Duration.ofMinutes(-100), "Интересный");
        final ValidationException exception = assertThrows(ValidationException.class,
                () -> manager.updateFilm(film));
        assertEquals("Продолжительность не может быть отрицательной", exception.getErrorMessage());
    }


}