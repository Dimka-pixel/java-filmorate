package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;

public interface UserStorage {
    void addUser(User user);

    HashMap getUsers();

    void deleteUser(int id);
}
