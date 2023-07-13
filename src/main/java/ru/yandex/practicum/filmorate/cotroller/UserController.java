package ru.yandex.practicum.filmorate.cotroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;


@RestController
public class UserController {

    @Autowired
    private final UserService manager;

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService manager) {
        this.manager = manager;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        log.info("Получен запрос GET/users");
        return manager.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Integer id) {
        log.info("Получен запрос GET/users{}", id);
        return manager.getUserById(id);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getFriends(@PathVariable int id) {
        log.info("Получен запрос GET/users/{}/friends", id);
        return manager.getFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getCommonFriend(@PathVariable Integer id, @PathVariable Integer otherId) {
        log.info("Получен запрос GET/users/{}/friends/common/{}", id, otherId);
        return manager.getCommonFriend(id, otherId);
    }

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) {
        log.info("Получен запрос POST/users");

        return manager.addUser(user);
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody @Valid User user) {
        log.info("Получен запрос PUT/users");
        manager.updateUser(user);
        return user;
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Получен запрос PUT/users/{}/friends/{}", id, friendId);
        manager.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Получен запрос DELETE/users/{}/friends/{}", id, friendId);
        manager.deletedFriend(id, friendId);
    }

}
