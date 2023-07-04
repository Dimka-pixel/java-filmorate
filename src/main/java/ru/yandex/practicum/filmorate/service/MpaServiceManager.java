package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage.MpaStorage;

import java.util.List;

@Service
public class MpaServiceManager {
    private final MpaStorage genreStorage;

    public MpaServiceManager(MpaStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Mpa getMpa(int id) {
        return genreStorage.getById(id);
    }

    public List<Mpa> getAllMpa() {
        return genreStorage.getAll();
    }

}
