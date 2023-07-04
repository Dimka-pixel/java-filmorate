package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genres;
import ru.yandex.practicum.filmorate.storage.GenreStorage.GenreStorage;

import java.util.List;

@Service
public class GenreServiceManager {

    private final GenreStorage genreStorage;

    public GenreServiceManager(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Genres getGenre(int id) {
        return genreStorage.getById(id);
    }

    public List<Genres> getAllGenres() {
        return genreStorage.getAll();
    }
}
