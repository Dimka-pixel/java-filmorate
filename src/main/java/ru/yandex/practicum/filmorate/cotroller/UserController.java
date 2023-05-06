package ru.yandex.practicum.filmorate.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserServiceManager;

import javax.validation.Valid;
import java.util.List;


@RestController
public class UserController {

    @Autowired
    private UserServiceManager manager;

    @GetMapping("/users")
    public List<User> getUsers() {
        return manager.getUsers();

    }

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) {
        manager.addUser(user);
        return user;

    }

    @PutMapping("/users")
    public User updateUser(@RequestBody @Valid User user) {
        manager.updateUser(user);
        return user;
    }

}
