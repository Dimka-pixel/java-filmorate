package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.GetFieldStorage;

import java.util.List;

@Service
public class MpaService {
    private final GetFieldStorage genreStorage;
    @Autowired
    public MpaService(@Qualifier("MPABean") GetFieldStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Mpa getMpa(int id) {
        return (Mpa) genreStorage.getById(id);
    }

    public List<Mpa> getAllMpa() {
        return genreStorage.getAll();
    }

}
