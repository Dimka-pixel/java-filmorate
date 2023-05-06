package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
public class FilmServiceManager {

    private int id;

    private HashMap<Integer, Film> films = new HashMap<>();
    private final Logger log = LoggerFactory.getLogger(FilmServiceManager.class);

    public void addFilm(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("ValidationException: Не корректная дата релиза");
            throw new ValidationException("дата релиза не может быть ранее 28 декабря 1895", BAD_REQUEST);
        } else if (film.getDuration().toMinutes() <= 0) {
            log.warn("ValidationException: Продолжительность имеет отрицательное значение");
            throw new ValidationException("Продолжительность не может быть отрицательной", BAD_REQUEST);
        } else {
            id++;
            film.setId(id);
            films.put(film.getId(), film);
            log.info("addFilm: добавлен новый фильм id:" + film.getId());
        }
    }

    public void updateFilm(Film film) {
        if (!(films.containsKey(film.getId()))) {
            log.warn("ValidationException: объекта нет в списке");
            throw new ValidationException("фильм не найден", NOT_FOUND);
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("ValidationException: Не корректная дата релиза");
            throw new ValidationException("дата релиза не может быть ранее 28 декабря 1895", BAD_REQUEST);
        } else if (film.getDuration().toMinutes() <= 0) {
            log.warn("ValidationException: Продолжительность имеет отрицательное значение");
            throw new ValidationException("Продолжительность не может быть отрицательной", BAD_REQUEST);
        } else {
            films.put(film.getId(), film);
            log.info("addFilm: Фильм с id: " + film.getId() + " успешно обновлён");
        }
    }

    public List<Film> getFilms() {
        log.info("получен GET запрос");
        List<Film> filmsList = new ArrayList<Film>(films.values());
        log.info("список фильмов отправлен");
        return filmsList;

    }


}