package ru.yandex.practicum.filmorate.storage;

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
public class InMemoryUserStorage implements UserStorage {

    private int id;

    private final Logger log = LoggerFactory.getLogger(InMemoryUserStorage.class);

    private HashMap<Integer, User> users = new HashMap<>();

    @Override
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

    @Override
    public void updateUser(User user) {
        if (!(users.containsKey(user.getId()))) {
            log.warn("User с ID: " + user.getId() + " не найден");
            throw new ValidationException("User с ID: " + user.getId() + " не найден", NOT_FOUND);
        } else if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("Полю name было присвоено значение поля login");
            users.get(user.getId()).setName(user.getName());
            users.get(user.getId()).setId(user.getId());
            users.get(user.getId()).setFriends(user.getFriends());
            users.get(user.getId()).setEmail(user.getEmail());
            users.get(user.getId()).setBirthday(user.getBirthday());
            users.get(user.getId()).setLogin(user.getLogin());

            log.info("User ID: " + user.getId() + " успешно обновлён");
        } else {
            users.get(user.getId()).setName(user.getName());
            users.get(user.getId()).setId(user.getId());
            users.get(user.getId()).setFriends(user.getFriends());
            users.get(user.getId()).setEmail(user.getEmail());
            users.get(user.getId()).setBirthday(user.getBirthday());
            users.get(user.getId()).setLogin(user.getLogin());
            log.info("User ID: " + user.getId() + " успешно обновлён");
        }


    }

    @Override
    public User getUserById(int id) {
        if (users.get(id) == null) {
            log.warn("ValidationException: объекта нет в списке");
            throw new ValidationException("User с ID " + id + " не найден", NOT_FOUND);
        } else {
            return users.get(id);
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>(users.values());
        log.info("список пользователей отправлен");
        return usersList;

    }

    @Override
    public HashMap<Integer, User> getUsers() {
        return users;
    }

    @Override
    public void deleteUser(int id) {
        users.remove(id);
    }
}
