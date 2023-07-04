package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage.UserStorage;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceManager {
    @Autowired
    @Qualifier("db")
    private final UserStorage userStorage;

    private final Logger log = LoggerFactory.getLogger(UserServiceManager.class);


    public UserServiceManager(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public int addUser(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        return userStorage.addObject(user);
    }

    public void updateUser(User user) {
        userStorage.updateObject(user);
    }

    public User getUserById(int id) {
        return userStorage.getObjectById(id);
    }

    public List<User> getAllUsers() {
        return userStorage.getAllObjects();
    }

    public void addFriend(int userId, int friendId) {
        userStorage.addFriend(userId, friendId);
    }

    public void deletedFriend(int userId, int friendId) {
        User user = userStorage.getObjectById(userId);
        user.getFriends().remove(friendId);
        userStorage.updateObject(user);

    }

    public List<User> getFriend(int id) {
        List<User> friends = new ArrayList<>();
        for (Integer friendId : userStorage.getObjectById(id).getFriends()
        ) {
            friends.add(userStorage.getObjectById(friendId));
        }

        return friends;

    }

    public List<User> getCommonFriend(int userId, int otherId) {
        List<User> commonFriend = new ArrayList<>();
        for (Integer friendId : userStorage.getObjectById(userId).getFriends()) {
            if (userStorage.getObjectById(otherId).getFriends().contains(friendId)) {
                commonFriend.add(userStorage.getObjectById(friendId));
            }
        }
        return commonFriend;
    }
}

