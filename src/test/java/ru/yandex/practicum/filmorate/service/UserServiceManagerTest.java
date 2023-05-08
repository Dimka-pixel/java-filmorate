package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceManagerTest {
    private UserServiceManager manager;

    @BeforeEach
    void beforeEach() {
        manager = new UserServiceManager();
    }

    @Test
    void shouldCreateUserIdAndSaveFilmInMap() {
        User user = new User("qwerty@mail.ru", "login", LocalDate.of(1987, 4, 9));
        user.setName("Name");
        manager.addUser(user);

        assertEquals(1, user.getId());
        assertEquals(user, manager.getUsers().get(0));
    }

    @Test
    void shouldAssignValueLoginForNameIfNameEqualNull() {
        User user = new User("qwerty@mail.ru", "login", LocalDate.of(1987, 4, 9));
        manager.addUser(user);

        assertEquals("login", user.getName());
    }

    @Test
    void shouldAssignValueLoginForNameIfNameIsEmpty() {
        User user = new User("qwerty@mail.ru", "login", LocalDate.of(1987, 4, 9));
        user.setName("");
        manager.addUser(user);

        assertEquals("login", user.getName());
    }

    @Test
    void shouldUpdateUser() {
        User user = new User("qwerty@mail.ru", "login", LocalDate.of(1987, 4, 9));
        user.setName("name");
        manager.addUser(user);
        User user2 = new User("qwerty@mail.ru", "login", LocalDate.of(1987, 4, 9));
        user2.setName("Update");
        user2.setId(1);
        manager.updateUser(user2);

        assertEquals("Update", manager.getUsers().get(0).getName());

    }

    @Test
    void shouldReturnValidationExceptionForUserUpdateIfMapIsEmpty() {
        User user = new User("qwerty@mail.ru", "login", LocalDate.of(1987, 4, 9));
        user.setName("name");
        final ValidationException exception = assertThrows(ValidationException.class,
                () -> manager.updateUser(user));
        assertEquals("User с ID: 0 не найден", exception.getErrorMessage());
    }

    @Test
    void shouldReturnValidationExceptionForUserUpdateIfIdIncorrect() {
        User user = new User("qwerty@mail.ru", "login", LocalDate.of(1987, 4, 9));
        user.setName("name");
        manager.addUser(user);
        User user2 = new User("qwerty@mail.ru", "login", LocalDate.of(1987, 4, 9));
        user2.setName("Update");
        user2.setId(999);

        final ValidationException exception = assertThrows(ValidationException.class,
                () -> manager.updateUser(user2));
        assertEquals("User с ID: 999 не найден", exception.getErrorMessage());
    }

    @Test
    void shouldUpdateUserAssignValueLoginForNameIfNameEqualNull() {
        User user = new User("qwerty@mail.ru", "login", LocalDate.of(1987, 4, 9));
        user.setName("name");
        manager.addUser(user);
        User user2 = new User("qwerty@mail.ru", "login", LocalDate.of(1987, 4, 9));
        user2.setId(1);
        manager.updateUser(user2);

        assertEquals("login", manager.getUsers().get(0).getName());
    }

    @Test
    void shouldUpdateUserAssignValueLoginForNameIfNameIsEmpty() {
        User user = new User("qwerty@mail.ru", "login", LocalDate.of(1987, 4, 9));
        user.setName("name");
        manager.addUser(user);
        User user2 = new User("qwerty@mail.ru", "login", LocalDate.of(1987, 4, 9));
        user2.setName("");
        user2.setId(1);
        manager.updateUser(user2);

        assertEquals("login", manager.getUsers().get(0).getName());
    }


}