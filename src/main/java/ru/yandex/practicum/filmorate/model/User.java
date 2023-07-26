package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("email", email);
        values.put("user_login", login);
        values.put("birthday", birthday);
        values.put("users_name", name);
        return values;
    }

}
