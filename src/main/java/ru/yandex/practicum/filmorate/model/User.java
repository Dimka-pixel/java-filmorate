package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

@Data
public class User {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{6,12}$")
    private String login;
    @PastOrPresent
    private LocalDate birthday;
    private int id;
    private String name;
    private Set<Integer> friends = new TreeSet<>();

}
