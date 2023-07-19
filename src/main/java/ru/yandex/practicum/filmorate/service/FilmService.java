package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.CrudStorage;
import ru.yandex.practicum.filmorate.storage.LikeStorage.LikeStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service("FilmServiceBean")
public class FilmService {
    private final CrudStorage filmStorage;

    private final LikeStorage likeStorage;

    private final CrudStorage userStorage;

    private final Logger log = LoggerFactory.getLogger(FilmService.class);

    @Autowired
    public FilmService(@Qualifier("FilmDb") CrudStorage filmStorage, LikeStorage likeStorage,
                       @Qualifier("UserDb") CrudStorage userStorage) {
        this.filmStorage = filmStorage;
        this.likeStorage = likeStorage;
        this.userStorage = userStorage;
    }

    public Film addFilm(Film film) {
        if (film != null && film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("ValidationException: Не корректная дата релиза");
            throw new ValidationException("Дата релиза не может быть ранее 28 декабря 1895", BAD_REQUEST);
        }
        int filmId = filmStorage.addObject(film);
        return (Film) filmStorage.getObjectById(filmId);
    }

    public Film updateFilm(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("ValidationException: Не корректная дата релиза");
            throw new ValidationException("Дата релиза не может быть ранее 28 декабря 1895", BAD_REQUEST);
        }
        filmStorage.updateObject(film);
        return (Film) filmStorage.getObjectById(film.getId());
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllObjects();

    }

    public Film getFilmById(int id) {
        if (filmStorage.getObjectById(id) == null) {
            log.warn("ValidationException: объекта нет в списке");
            throw new ValidationException("Фильм с ID " + id + " не найден", NOT_FOUND);
        } else {
            log.info("Фильм ID " + id + "отправлен");
            return (Film) filmStorage.getObjectById(id);
        }

    }

    public List<Film> getTopFilms(Integer count) {
        ArrayList<Integer> topFilms = new ArrayList<>(likeStorage.getTopFilmForLikes());
        if (topFilms.isEmpty()) {
            return getAllFilms();
        }
        ArrayList<Film> films = new ArrayList<>();
        if (count == null) {
            if (topFilms.size() < 10) {
                for (Integer filmID : topFilms) {
                    films.add(getFilmById(filmID));
                }
                log.info("Отправлен список из " + topFilms.size() + " TOP фильмов");
            } else {
                for (int i = 0; i < 10; i++) {
                    films.add(getFilmById(topFilms.get(i)));
                }
                log.info("Отправлен список из 10 TOP фильмов");
            }
        } else {
            if (count <= topFilms.size() && count > 0) {
                for (int i = 0; i < count; i++) {
                    films.add(getFilmById(topFilms.get(i)));
                }
                log.info("Отправлен список из " + count + " TOP фильмов");
            } else {
                log.warn("Не корректное количество фильмов для формироваия запроса");
                throw new ValidationException("Не корректное количество фильмов count: " + count, BAD_REQUEST);
            }
        }
        return films;
    }

    public Film putLike(int userId, int filmId) {
        log.info("Лайк добавлен");
        likeStorage.addLike(userId, filmId);
        return (Film) filmStorage.getObjectById(userId);

    }

    public void deleteLike(int filmId, int userId) {
        likeStorage.deleteLike(filmId, userId);
    }
}