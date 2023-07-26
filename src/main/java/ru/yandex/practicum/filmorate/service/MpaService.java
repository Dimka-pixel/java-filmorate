package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage.MpaStorage;

import java.util.List;

@Service
public class MpaService {
    private final MpaStorage genreStorage;

    @Autowired
    public MpaService(MpaStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Mpa getMpa(int id) {
        return (Mpa) genreStorage.getById(id);
    }

    public List<Mpa> getAllMpa() {
        return genreStorage.getAll();
    }

}
