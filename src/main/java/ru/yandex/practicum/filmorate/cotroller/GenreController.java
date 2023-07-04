package ru.yandex.practicum.filmorate.cotroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genres;
import ru.yandex.practicum.filmorate.service.GenreServiceManager;

import java.util.List;

@RestController
public class GenreController {

    private final Logger log = LoggerFactory.getLogger(GenreController.class);
    private final GenreServiceManager manager;

    public GenreController(GenreServiceManager manager) {
        this.manager = manager;
    }

    @GetMapping("genres/{id}")
    public Genres getGenres(@PathVariable Integer id) {
        log.info("получен запрос GET/genres/" + id);
        return manager.getGenre(id);

    }

    @GetMapping("/genres")
    public List<Genres> getAllGenre() {
        log.info("получен запрос GET/genres");
        return manager.getAllGenres();
    }
}
