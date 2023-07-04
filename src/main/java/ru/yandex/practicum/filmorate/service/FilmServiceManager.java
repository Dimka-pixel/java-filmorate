package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage.UserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class FilmServiceManager {
    @Autowired
    @Qualifier("FilmDb")
    private final FilmStorage filmStorage;

    @Autowired
    @Qualifier("db")
    private final UserStorage userStorage;

    private final Logger log = LoggerFactory.getLogger(FilmServiceManager.class);

    public FilmServiceManager(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public int addFilm(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("ValidationException: Не корректная дата релиза");
            throw new ValidationException("Дата релиза не может быть ранее 28 декабря 1895", BAD_REQUEST);
        } else if (film.getDuration().toMinutes() <= 0) {
            log.warn("Продолжительность фильма отрицательная");
            throw new ValidationException("Продолжительность не может быть отрицательной", BAD_REQUEST);
        }
        return filmStorage.addObject(film);
    }

    public void updateFilm(Film film) {
        filmStorage.updateObject(film);
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
            return filmStorage.getObjectById(id);
        }

    }

    public List<Film> getTopFilms(Integer count) {
        List<Film> topFilms = getAllFilms();
        if (topFilms.size() <= 1) {
            log.info("Отправлен список из " + topFilms.size() + " TOP фильмов");
            return topFilms;
        }
        topFilms.sort((Film film1, Film film2) -> {
            if (film1.getLikes().size() == 0) {
                return 1;
            }
            if (film2.getLikes().size() == 0) {
                return -1;
            }
            return film1.getLikes().size() - film2.getLikes().size();
        });
        List<Film> newTopFilms = new ArrayList<>();
        if (count == null) {
            if (topFilms.size() < 10) {
                for (int i = 0; i < topFilms.size(); i++) {
                    newTopFilms.add(topFilms.get(i));
                }
                log.info("Отправлен список из " + topFilms.size() + " TOP фильмов");
            } else {
                for (int i = 0; i < 10; i++) {
                    newTopFilms.add(topFilms.get(i));
                }
                log.info("Отправлен список из 10 TOP фильмов");
            }
        } else {
            if (count <= topFilms.size() && count > 0) {
                for (int i = 0; i < count; i++) {
                    newTopFilms.add(topFilms.get(i));
                }
                log.info("Отправлен список из " + count + " TOP фильмов");
            } else {
                log.warn("Не корректное количество фильмов для формироваия запроса");
                throw new ValidationException("Не корректное количество фильмов count: " + count, BAD_REQUEST);
            }
        }
        return newTopFilms;
    }

    public void putLike(int userId, int filmId) {
        filmStorage.addLike(userId, filmId);
        log.info("Лайк добавлен");
    }

    public void deleteLike(int filmId, int userId) {
        Film filmLike = getFilmById(filmId);
        if (!(filmLike.getLikes().contains(userId))) {
            log.warn("Like c userId " + userId + " не найден");
            throw new ValidationException("Like c userId " + userId + " не найден", NOT_FOUND);
        } else {
            log.info("Like c userId " + userId + " удалён");
            getFilmById(filmId).getLikes().remove(userId);
        }
    }
}