package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class User {
    @Email
    private final String email;
    @NotBlank
    private final String login;
    @Past
    private final LocalDate birthday;
    private int id;
    private String name;

}
