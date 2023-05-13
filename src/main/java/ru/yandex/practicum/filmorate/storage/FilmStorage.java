package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;

public interface FilmStorage {
    void addFilm(Film film);

    Film getFilmById(int id);

    HashMap<Integer, Film> getFilms();

    void deleteFilm(int id);
}
