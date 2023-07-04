package ru.yandex.practicum.filmorate.storage.FilmStorage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.CrudStorage;

public interface FilmStorage extends CrudStorage<Film> {

    public void addLike(int userId, int filmId);

}
