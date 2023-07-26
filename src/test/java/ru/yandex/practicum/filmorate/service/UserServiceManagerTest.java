package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendStorage.FriendStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage.UserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceManagerTest {

    @Autowired
    private UserStorage userStorage;

    @Autowired
    FriendStorage friendStorage;

    @Autowired
    private UserService manager;


    @Test
    void shouldCreateUserIdAndSaveFilmInMap() {
        User user = new User();
        user.setEmail("qwerty@mail.ru");
        user.setLogin("login");
        user.setBirthday(LocalDate.of(1987, 4, 9));
        user.setName("Name");
        manager.addUser(user);
        assertEquals(1, user.getId());
        assertEquals(user, manager.getAllUsers().get(0));
    }

    @Test
    void shouldAssignValueLoginForNameIfNameEqualNull() {
        User user = new User();
        user.setEmail("qwerty@mail.ru");
        user.setLogin("login");
        user.setBirthday(LocalDate.of(1987, 4, 9));
        manager.addUser(user);

        assertEquals("login", user.getName());
    }

    private User user;

    private User user2;

    @BeforeEach
    private void beforeEach() {
        user = new User();
        user.setEmail("qwerty@mail.ru");
        user.setLogin("login");
        user.setBirthday(LocalDate.of(1987, 4, 9));
        user.setName("Name");

        user2 = new User();
        user2.setEmail("qwerty@mail.ru");
        user2.setLogin("login");
        user2.setBirthday(LocalDate.of(1987, 4, 9));
        user2.setName("Update");

    }

    @Test
    void shouldAssignValueLoginForNameIfNameIsEmpty() {
        user.setName("");
        manager.addUser(user);

        assertEquals("login", user.getName());
    }

    @Test
    void shouldUpdateUser() {
        user.setName("Name");
        manager.addUser(user);
        user2.setId(1);
        manager.updateUser(user2);

        assertEquals("Update", manager.getAllUsers().get(0).getName());

    }

    @Test
    void shouldReturnValidationExceptionForUserUpdateIfMapIsEmpty() {
        final ValidationException exception = assertThrows(ValidationException.class,
                () -> manager.updateUser(user));
        assertEquals("User с ID: 0 не найден", exception.getErrorMessage());
    }

    @Test
    void shouldReturnValidationExceptionForUserUpdateIfIdIncorrect() {
        manager.addUser(user);
        user2.setId(999);

        final ValidationException exception = assertThrows(ValidationException.class,
                () -> manager.updateUser(user2));
        assertEquals("User с ID: 999 не найден", exception.getErrorMessage());
    }

    @Test
    void shouldUpdateUserAssignValueLoginForNameIfNameEqualNull() {
        manager.addUser(user);
        user2.setId(1);
        manager.updateUser(user2);

        assertEquals("login", manager.getAllUsers().get(0).getName());
    }

    @Test
    void shouldUpdateUserAssignValueLoginForNameIfNameIsEmpty() {
        manager.addUser(user);
        user2.setName("");
        user2.setId(1);
        manager.updateUser(user2);

        assertEquals("login", manager.getAllUsers().get(0).getName());
    }


}