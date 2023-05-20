package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.List;

public interface UserStorage {
    void addUser(User user);

    void updateUser(User user);

    User getUserById(int id);

    List<User> getAllUsers();

    HashMap<Integer, User> getUsers();

    void deleteUser(int id);


}
