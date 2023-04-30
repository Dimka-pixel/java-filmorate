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
    final private String email;
    @NotBlank
    final private String login;
    @Past
    final private LocalDate birthday;
    private int id;
    private String name;

}
