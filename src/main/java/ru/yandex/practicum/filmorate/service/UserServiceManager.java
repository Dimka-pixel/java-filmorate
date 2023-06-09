package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@Service
public class UserServiceManager {
    private final UserStorage userStorage;

    private final Logger log = LoggerFactory.getLogger(UserServiceManager.class);

    @Autowired
    public UserServiceManager(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addUser(User user) {
        userStorage.addUser(user);
    }

    public void updateUser(User user) {
        userStorage.updateUser(user);
    }

    public User getUserById(int id) {
        return userStorage.getUserById(id);
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public void addFriend(int userId, int friendId) {
        if (!(userStorage.getUsers().containsKey(userId))) {
            log.warn("ValidationException: User с ID " + userId + " не найден");
            throw new ValidationException("User с ID " + userId + " не найден", NOT_FOUND);
        } else if (!(userStorage.getUsers().containsKey(friendId))) {
            log.warn("ValidationException: User с ID " + friendId + " не найден");
            throw new ValidationException("User с ID " + friendId + " не найден", NOT_FOUND);
        } else if (!(userStorage.getUsers().containsKey(friendId)) && !(userStorage.getUsers().containsKey(userId))) {
            log.warn("ValidationException: Users не найдены");
            throw new ValidationException("Users не найдены", NOT_FOUND);
        } else {
            log.info("User с ID " + userId + " добавил в друзья пользователя с ID " + friendId);
            userStorage.getUsers().get(friendId).getFriends().add(userId);
            userStorage.getUsers().get(userId).getFriends().add(friendId);
        }
    }

    public void deletedFriend(int userId, int friendId) {
        if (!(userStorage.getUsers().containsKey(userId))) {
            log.warn("ValidationException: User с ID " + userId + " не найден");
            throw new ValidationException("User с ID " + userId + " не найден", NOT_FOUND);
        } else if (!(userStorage.getUsers().containsKey(friendId))) {
            log.warn("ValidationException: User с ID " + friendId + " не найден");
            throw new ValidationException("User с ID " + friendId + " не найден", NOT_FOUND);
        } else if (!(userStorage.getUsers().containsKey(friendId)) && !(userStorage.getUsers().containsKey(userId))) {
            log.warn("ValidationException: Users не найдены");
            throw new ValidationException("Users не найдены", NOT_FOUND);
        } else {
            log.warn("User с ID " + userId + " удалил из друзей пользователя с ID " + friendId);
            userStorage.getUsers().get(friendId).getFriends().remove(userId);
            userStorage.getUsers().get(userId).getFriends().remove(friendId);
        }
    }

    public List<User> getFriend(int id) {
        List<User> friends = new ArrayList<>();
        if (userStorage.getUsers().containsKey(id)) {
            for (Integer userId : userStorage.getUserById(id).getFriends()) {
                friends.add(userStorage.getUserById(userId));
            }
            log.info("список друзей сформирован");
        } else {
            log.warn("User c ID " + id + "не найден");
            throw new ValidationException("User c ID " + id + "не найден", NOT_FOUND);
        }
        log.info("список друзей отправлен");
        return friends;

    }

    public List<User> getCommonFriend(int userId, int otherId) {
        List<User> commonFriend = new ArrayList<>();
        if (!(userStorage.getUsers().containsKey(userId))) {
            log.warn("ValidationException: User с ID " + userId + " не найден");
            throw new ValidationException("User с ID " + userId + " не найден", NOT_FOUND);
        } else if (!(userStorage.getUsers().containsKey(otherId))) {
            log.warn("ValidationException: User с ID " + otherId + " не найден");
            throw new ValidationException("User с ID " + otherId + " не найден", NOT_FOUND);
        } else if (!(userStorage.getUsers().containsKey(otherId)) && !(userStorage.getUsers().containsKey(userId))) {
            log.warn("ValidationException: Users не найдены");
            throw new ValidationException("Users не найдены", NOT_FOUND);
        } else {
            for (Integer friendId : userStorage.getUserById(userId).getFriends()) {
                if (userStorage.getUserById(otherId).getFriends().contains(friendId)) {
                    commonFriend.add(userStorage.getUserById(friendId));
                }
            }
        }
        return commonFriend;
    }
}

