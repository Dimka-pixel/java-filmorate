package ru.yandex.practicum.filmorate.storage.UserStorage;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.CrudStorage;

public interface UserStorage extends CrudStorage<User> {

    public void addFriend(int userId, int friendId);


}
