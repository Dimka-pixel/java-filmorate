package ru.yandex.practicum.filmorate.Validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserValidatorTest {
    private UserValidator validator;

    @BeforeEach
    void beforeEach() {
        validator = new UserValidator();
    }

    @Test
    void shouldAssignValueLoginForName() {
        User user = new User("qwerty@mail.ru", "login", LocalDate.of(1987, 4, 9));
        validator.validate(user);

        assertEquals("login", user.getName());
    }

}