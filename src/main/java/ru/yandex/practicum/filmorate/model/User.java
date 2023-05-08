package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class User {
    @Email
    @NotBlank
    private final String email;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{6,12}$")
    private final String login;
    @PastOrPresent
    private final LocalDate birthday;
    private int id;
    private String name;

}
