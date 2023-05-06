package ru.yandex.practicum.filmorate.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmServiceManager;

import javax.validation.Valid;
import java.util.List;

@RestController
public class FilmController {

    @Autowired
    private FilmServiceManager manager;

    @GetMapping("/films")
    public List<Film> getFilms() {
        return manager.getFilms();
    }

    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) {
        manager.addFilm(film);
        return film;
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) {
        manager.updateFilm(film);
        return film;
    }
}

