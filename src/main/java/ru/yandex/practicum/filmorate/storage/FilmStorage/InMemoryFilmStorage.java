package ru.yandex.practicum.filmorate.storage.FilmStorage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmServiceManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component("mem")
public class InMemoryFilmStorage implements FilmStorage {

    private int id;

    private final Logger log = LoggerFactory.getLogger(FilmServiceManager.class);

    private final HashMap<Integer, Film> films = new HashMap<>();

    @Override
    public int addObject(Film film) {
        if (film.getReleaseDate() != null && film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("ValidationException: Не корректная дата релиза");
            throw new ValidationException("дата релиза не может быть ранее 28 декабря 1895", BAD_REQUEST);
        } else if (film.getDuration() != null && film.getDuration().toMinutes() <= 0) {
            log.warn("ValidationException: Продолжительность имеет отрицательное значение");
            throw new ValidationException("Продолжительность не может быть отрицательной", BAD_REQUEST);
        } else {
            if (film.getReleaseDate() != null && film.getDuration() != null) {
                id++;
                film.setId(id);
                films.put(film.getId(), film);
                log.info("addFilm: добавлен новый фильм id:" + film.getId());
            }
        }
        return id;
    }

    @Override
    public void updateObject(Film film) {
        if (!(films.containsKey(film.getId()))) {
            log.warn("ValidationException: объекта нет в списке");
            throw new ValidationException("фильм не найден", NOT_FOUND);
        } else if (film.getReleaseDate() != null && film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("ValidationException: Не корректная дата релиза");
            throw new ValidationException("дата релиза не может быть ранее 28 декабря 1895", BAD_REQUEST);
        } else if (film.getDuration() != null && film.getDuration().toMinutes() <= 0) {
            log.warn("ValidationException: Продолжительность имеет отрицательное значение");
            throw new ValidationException("Продолжительность не может быть отрицательной", BAD_REQUEST);
        } else {
            if (film.getReleaseDate() != null && film.getDuration() != null) {
                films.get(film.getId()).setReleaseDate(film.getReleaseDate());
                films.get(film.getId()).setDescription(film.getDescription());
                films.get(film.getId()).setName(film.getName());
                films.get(film.getId()).setId(film.getId());
                films.get(film.getId()).setDuration(film.getDuration());
                films.get(film.getId()).setLikes(film.getLikes());
                log.info("addFilm: Фильм с id: " + film.getId() + " успешно обновлён");
            }
        }

    }

    @Override
    public List<Film> getAllObjects() {
        List<Film> filmsList = new ArrayList<Film>(films.values());
        log.info("список фильмов отправлен");
        return filmsList;
    }

    @Override
    public void deleteObject(int id) {
        films.remove(id);
    }

    @Override
    public Film getObjectById(int id) {
        if (films.get(id) == null) {
            log.warn("ValidationException: объекта нет в списке");
            throw new ValidationException("Фильм с ID " + id + " не найден", NOT_FOUND);
        } else {
            log.info("Фильм ID " + id + "отправлен");
            return films.get(id);
        }

    }

    public HashMap<Integer, Film> getFilms() {
        return films;
    }

    @Override
    public void addLike(int userId, int filmId) {

    }
}

