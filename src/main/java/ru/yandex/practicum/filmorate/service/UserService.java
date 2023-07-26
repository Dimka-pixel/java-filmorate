package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendStorage.FriendStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage.UserStorage;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {
    private final UserStorage userStorage;

    private final FriendStorage friendStorage;

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserStorage userStorage, FriendStorage friendStorage) {
        this.userStorage = userStorage;
        this.friendStorage = friendStorage;
    }

    public User addUser(User user) {
        if (user != null) {
            if (user.getName() == null || user.getName().isBlank()) {
                user.setName(user.getLogin());
            }
        }
        int userId = userStorage.addObject(user);
        return (User) userStorage.getObjectById(userId);
    }

    public User updateUser(User user) {
        userStorage.updateObject(user);
        return (User) userStorage.getObjectById(user.getId());
    }

    public User getUserById(int id) {
        return (User) userStorage.getObjectById(id);
    }

    public List<User> getAllUsers() {
        return userStorage.getAllObjects();
    }

    public User addFriend(int userId, int friendId) {
        friendStorage.addFriend(userId, friendId);
        return (User) userStorage.getObjectById(userId);
    }

    public void deletedFriend(int userId, int friendId) {
        friendStorage.deletedFriend(userId, friendId);
    }

    public List<User> getFriends(int id) {
        List<User> friends = new ArrayList<>();
        for (Integer friendId : friendStorage.getFriends(id)
        ) {
            friends.add((User) userStorage.getObjectById(friendId));
        }

        return friends;

    }

    public List<User> getCommonFriend(int userId, int otherId) {
        List<User> commonFriend = new ArrayList<>();
        for (Integer friendId : friendStorage.getCommonFriend(userId, otherId)) {
            commonFriend.add((User) userStorage.getObjectById(friendId));
        }
        return commonFriend;
    }
}

