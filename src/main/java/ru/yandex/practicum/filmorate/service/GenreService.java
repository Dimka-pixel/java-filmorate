package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genres;
import ru.yandex.practicum.filmorate.storage.GetFieldStorage;

import java.util.List;

@Service
public class GenreService {

    private final GetFieldStorage genreStorage;

    @Autowired
    public GenreService(@Qualifier("genreBean") GetFieldStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Genres getGenre(int id) {
        return (Genres) genreStorage.getById(id);
    }

    public List<Genres> getAllGenres() {
        return genreStorage.getAll();
    }
}
