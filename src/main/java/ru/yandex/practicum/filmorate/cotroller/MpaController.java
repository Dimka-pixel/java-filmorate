package ru.yandex.practicum.filmorate.cotroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaServiceManager;

import java.util.List;


@RestController
public class MpaController {

    private final Logger log = LoggerFactory.getLogger(MpaController.class);

    private final MpaServiceManager manager;

    public MpaController(MpaServiceManager manager) {
        this.manager = manager;
    }

    @GetMapping("/mpa/{id}")
    public Mpa getMpa(@PathVariable Integer id) {
        log.info("получен запрос GET/Mpa/" + id);
        return manager.getMpa(id);

    }

    @GetMapping("/mpa")
    public List<Mpa> getAllGenre() {
        log.info("получен запрос GET/mpa");
        return manager.getAllMpa();
    }


}
