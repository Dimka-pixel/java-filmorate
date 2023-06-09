package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class FilmServiceManager {

    private final FilmStorage filmStorage;

    private final UserStorage userStorage;

    private final Logger log = LoggerFactory.getLogger(FilmServiceManager.class);

    @Autowired
    public FilmServiceManager(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void addFilm(Film film) {
        filmStorage.addFilm(film);
    }

    public void updateFilm(Film film) {
        filmStorage.updateFilm(film);
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();

    }

    public Film getFilmById(int id) {
        if (filmStorage.getFilmById(id) == null) {
            log.warn("ValidationException: объекта нет в списке");
            throw new ValidationException("Фильм с ID " + id + " не найден", NOT_FOUND);
        } else {
            log.info("Фильм ID " + id + "отправлен");
            return filmStorage.getFilmById(id);
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
        if (!(userStorage.getUsers().containsKey(userId))) {
            log.warn("Передан не корректный UserId " + userId);
            throw new ValidationException("User c id " + userId + " не найден", NOT_FOUND);
        }
        if (!(filmStorage.getFilms().containsKey(filmId))) {
            log.warn("Передан не корректный filmId " + filmId);
            throw new ValidationException("Film c id " + filmId + " не найден", NOT_FOUND);
        }
        log.info("Лайк добавлен");
        getFilmById(filmId).getLikes().add(userId);
    }

    public void deleteLike(int filmId, int userId) {
        if (!(filmStorage.getFilms().containsKey(filmId))) {
            log.warn("Film c ID " + filmId + " не найден");
            throw new ValidationException("Film c ID " + filmId + " не найден", NOT_FOUND);
        } else if (!(filmStorage.getFilmById(filmId).getLikes().contains(userId))) {
            log.warn("Like c userId " + userId + " не найден");
            throw new ValidationException("Like c userId " + userId + " не найден", NOT_FOUND);
        } else {
            log.info("Like c userId " + userId + " удалён");
            getFilmById(filmId).getLikes().remove(userId);
        }
    }
}