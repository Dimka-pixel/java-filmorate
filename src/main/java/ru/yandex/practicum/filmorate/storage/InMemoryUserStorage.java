package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;

@Component
public class InMemoryUserStorage implements UserStorage {

    private HashMap<Integer,User> users = new HashMap<>();

    @Override
    public void addUser(User user) {
        users.put(user.getId(),user);
    }

    @Override
    public HashMap getUsers() {
        return users;
    }

    @Override
    public void deleteUser(int id) {
        users.remove(id);
    }
}
