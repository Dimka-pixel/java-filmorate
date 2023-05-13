package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;

@Component
public class InMemoryUserStorage implements UserStorage {

    private HashMap<Integer, User> users = new HashMap<>();

    @Override
    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public User getUserById(int id) {
        return users.get(id);
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
