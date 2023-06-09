package ru.yandex.practicum.filmorate.cotroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmServiceManager;

import javax.validation.Valid;
import java.util.List;

@RestController
public class FilmController {

    @Autowired
    private final FilmServiceManager manager;

    private final Logger log = LoggerFactory.getLogger(FilmController.class);

    public FilmController(FilmServiceManager manager) {
        this.manager = manager;
    }

    @GetMapping("/films")
    public List<Film> getFilms() {
        log.info("Получен запрос GET/films");
        return manager.getAllFilms();
    }

    @GetMapping("/films/{id}")
    public Film getFilmsById(@PathVariable Integer id) {
        log.info("Получен запрос GET/films/" + id);
        return manager.getFilmById(id);
    }

    @GetMapping("/films/popular")
    public List<Film> getTopFilms(@RequestParam(required = false) Integer count) {
        log.info("Получен запрос GET/films/popular");
        return manager.getTopFilms(count);
    }

    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос POST/films");
        manager.addFilm(film);
        return film;
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос PUT/films");
        manager.updateFilm(film);
        return film;
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("Получен запрос PUT/films/" + id + "/like/" + userId);
        manager.putLike(userId, id);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("Получен запрос DELETE/films/" + id + "/like/" + userId);
        manager.deleteLike(id, userId);
    }

}

