package ru.yandex.practicum.filmorate.Validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
public class FilmValidator {
    private final Logger log = LoggerFactory.getLogger(FilmValidator.class);

    public void validate(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("ValidationException: Не корректная дата релиза");
            throw new ValidationException("Одата релиза не может быть ранее 28 декабря 1895", BAD_REQUEST);
        } else if (film.getDuration().toMinutes() <= 0) {
            log.warn("ValidationException: Продолжительность имеет отрицательное значение");
            throw new ValidationException("Продолжительность не может быть отрицательной", BAD_REQUEST);
        }
    }

}