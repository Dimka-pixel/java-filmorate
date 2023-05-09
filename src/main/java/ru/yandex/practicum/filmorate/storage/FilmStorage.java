package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;

public interface FilmStorage {
    void addFilm(Film film);

    HashMap getFilms();

    void deleteFilm(int id);
}
