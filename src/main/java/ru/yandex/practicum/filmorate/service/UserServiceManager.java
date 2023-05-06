package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@Component
public class UserServiceManager {

    private int id;
    private HashMap<Integer, User> users = new HashMap<>();
    private final Logger log = LoggerFactory.getLogger(UserServiceManager.class);

    public void addUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("Полю name было присвоено значение поля login");
        }
        id++;
        user.setId(id);
        users.put(user.getId(), user);
        log.info("User ID: " + user.getId() + " успешно добавлен");
    }

    public void updateUser(User user) {
        if (!(users.containsKey(user.getId()))) {
            log.warn("User с ID: " + user.getId() + " не найден");
            throw new ValidationException("User с ID: " + user.getId() + " не найден", NOT_FOUND);
        } else if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("Полю name было присвоено значение поля login");
            users.put(user.getId(), user);
            log.info("User ID: " + user.getId() + " успешно обновлён");
        } else {
            users.put(user.getId(), user);
            log.info("User ID: " + user.getId() + " успешно обновлён");
        }

    }

    public List<User> getUsers() {
        log.info("получен GET запрос");
        List<User> usersList = new ArrayList<User>(users.values());
        log.info("список пользователей отправлен");
        return usersList;
    }

}

