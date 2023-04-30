package ru.yandex.practicum.filmorate.Cotroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
public class FilmController {
    private static int id;

    private final Logger log = LoggerFactory.getLogger(FilmController.class);

    private HashMap<Integer, Film> films = new HashMap<>();

    @GetMapping("/films")
    public List<Film> getFilms() {
        log.info("получен GET запрос");
        List<Film> filmsList = new ArrayList<Film>(films.values());
        log.info("список фильмов отправлен");
        return filmsList;

    }

    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("ValidationException: Не корректная дата релиза");
            throw new ValidationException("Одата релиза не может быть ранее 28 декабря 1895", BAD_REQUEST);
        } else if (film.getDescription().length() >= 200) {
            log.warn("ValidationException: Описание объекта превышает допустимый объем");
            throw new ValidationException("Описание объекта превышает допустимый объем, колличество символов должно" +
                    " быть не больше 200", BAD_REQUEST);
        } else if (film.getDuration().toMinutes() < 0) {
            log.warn("ValidationException: продолжительность фильма имеет отрицательное значение");
            throw new ValidationException("продолжительность фильма не может иметь отрицательное значение", BAD_REQUEST);
        } else {
            id++;
            film.setId(id);
            films.put(film.getId(), film);
            log.info("addFilm: добавлен новый фильм id:" + film.getId());
        }
        return film;
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film) {
        if (!(films.containsKey(film.getId()))) {
            log.warn("ValidationException: объекта нет в списке");
            throw new ValidationException("фильм не найден", NOT_FOUND);
        } else if (film.getDescription().length() >= 200) {
            log.warn("ValidationException: Описание объекта превышает допустимый объем");
            throw new ValidationException("Описание объекта превышает допустимый объем, колличество символов должно" +
                    " быть не больше 200", BAD_REQUEST);
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("ValidationException: Не корректная дата релиза");
            throw new ValidationException("Одата релиза не может быть ранее 28 декабря 1895", BAD_REQUEST);
        } else if (film.getDuration().toMinutes() < 0) {
            log.warn("ValidationException: продолжительность фильма имеет отрицательное значение");
            throw new ValidationException("продолжительность фильма не может иметь отрицательное значение", BAD_REQUEST);
        } else {
            films.put(film.getId(), film);
            log.info("addFilm: Фильм с id: " + film.getId() + " успешно обновлён");
            return film;
        }

    }
}

