package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage.UserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmServiceManagerTest {
    @Autowired
    private FilmService manager;

    @Autowired
    private FilmStorage filmStorage;

    @Autowired
    private UserStorage userStorage;

    private Film film;

    private Film film2;

    @BeforeEach
    private void beforeEach() {
        film = new Film();
        film.setName("Боевик");
        film.setReleaseDate(LocalDate.of(1900, 2, 3));
        film.setDuration(100);
        film.setDescription("Интересный");

        film2 = new Film();
        film2.setName("Update");
        film2.setReleaseDate(LocalDate.of(1900, 2, 3));
        film2.setDuration(100);
        film2.setDescription("Интересный");

    }


    @Test
    void shouldCreateFilmIdAndSaveFilmInMap() {

        manager.addFilm(film);

        assertEquals(1, film.getId());
        assertEquals(film, manager.getAllFilms().get(0));
    }

    @Test
    void shouldReturnValidationExceptionIfDateReleaseBefore18951228() {
        film.setReleaseDate(LocalDate.of(1800, 2, 3));

        final ValidationException exception = assertThrows(ValidationException.class,
                () -> manager.addFilm(film));
        assertEquals("Дата релиза не может быть ранее 28 декабря 1895", exception.getErrorMessage());
    }

    @Test
    void shouldReturnValidationExceptionIfDurationLessZero() {
        film.setDuration(-100);
        final ValidationException exception = assertThrows(ValidationException.class,
                () -> manager.addFilm(film));
        assertEquals("Продолжительность не может быть отрицательной", exception.getErrorMessage());
    }

    @Test
    void shouldUpdateFilm() {
        manager.addFilm(film);
        film2.setId(1);
        manager.updateFilm(film2);

        assertEquals("Update", manager.getAllFilms().get(0).getName());
    }

    @Test
    void shouldReturnValidationExceptionForFilmUpdateIfMapIsEmpty() {
        final ValidationException exception = assertThrows(ValidationException.class,
                () -> manager.updateFilm(film));
        assertEquals("фильм не найден", exception.getErrorMessage());
    }

    @Test
    void shouldReturnValidationExceptionForUpdateFilmIfDateReleaseBefore18951228() {
        manager.addFilm(film);
        film2.setReleaseDate(LocalDate.of(1800, 2, 3));
        film2.setId(1);
        final ValidationException exception = assertThrows(ValidationException.class,
                () -> manager.updateFilm(film2));
        assertEquals("дата релиза не может быть ранее 28 декабря 1895", exception.getErrorMessage());
    }

    @Test
    void shouldReturnValidationExceptionForUpdateFilmIfDurationLessZero() {
        manager.addFilm(film);
        film2.setDuration(-100);
        film2.setId(1);
        final ValidationException exception = assertThrows(ValidationException.class,
                () -> manager.updateFilm(film2));
        assertEquals("Продолжительность не может быть отрицательной", exception.getErrorMessage());
    }

}