package ru.yandex.practicum.filmorate.Validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;


@Component
public class UserValidator {
    private final Logger log = LoggerFactory.getLogger(UserValidator.class);

    public void validate(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
            log.info("Полю name было присвоено значение поля login");
        }
    }
}

