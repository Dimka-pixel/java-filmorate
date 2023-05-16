package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;

public interface FilmStorage {
    void addFilm(Film film);

    void updateFilm(Film film);

    Film getFilmById(int id);

    List<Film> getAllFilms();

    HashMap<Integer, Film> getFilms();

    void deleteFilm(int id);
}
