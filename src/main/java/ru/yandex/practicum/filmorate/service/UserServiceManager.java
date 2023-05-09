package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@Service
public class UserServiceManager {
    private UserStorage userStorage;
    private int id;
    private HashMap<Integer, User> users = new HashMap<>();
    private final Logger log = LoggerFactory.getLogger(UserServiceManager.class);
    @Autowired
    public UserServiceManager(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("Полю name было присвоено значение поля login");
        }
        id++;
        user.setId(id);
        userStorage.addUser(user);
        log.info("User ID: " + user.getId() + " успешно добавлен");
    }

    public void updateUser(User user) {
        if (!(userStorage.getUsers().containsKey(user.getId()))) {
            log.warn("User с ID: " + user.getId() + " не найден");
            throw new ValidationException("User с ID: " + user.getId() + " не найден", NOT_FOUND);
        } else if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("Полю name было присвоено значение поля login");
            userStorage.addUser(user);
            log.info("User ID: " + user.getId() + " успешно обновлён");
        } else {
            userStorage.addUser(user);
            log.info("User ID: " + user.getId() + " успешно обновлён");
        }

    }

    public List<User> getUsers() {
        log.info("получен GET запрос");
        List<User> usersList = new ArrayList<User>(userStorage.getUsers().values());
        log.info("список пользователей отправлен");
        return usersList;
    }

}

