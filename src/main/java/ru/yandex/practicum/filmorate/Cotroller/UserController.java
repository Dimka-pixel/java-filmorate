package ru.yandex.practicum.filmorate.Cotroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.Validation.UserValidator;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@RestController
public class UserController {

    private static int id;
    private HashMap<Integer, User> users = new HashMap<>();

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserValidator validatorUser;

    @GetMapping("/users")
    public List<User> getUsers() {
        List<User> usersList = new ArrayList<User>(users.values());
        return usersList;

    }

    @PostMapping("/users")
    public User updateUser(@Valid @RequestBody User user) {
        validatorUser.validate(user);
        id++;
        user.setId(id);
        users.put(user.getId(), user);
        log.info("User ID: " + user.getId() + " успешно добавлен");
        return user;

    }

    @PutMapping("/users")
    public User addUser(@RequestBody @Valid User user) {
        validatorUser.validate(user);
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("User ID: " + user.getId() + " успешно обновлён");
        } else {
            log.warn("User с ID: " + user.getId() + " не найден");
            throw new ValidationException("User с ID: " + user.getId() + " не найден", NOT_FOUND);
        }
        return user;
    }

}
