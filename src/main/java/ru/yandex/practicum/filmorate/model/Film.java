package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    @NotBlank
    private String name;
    private LocalDate releaseDate;
    private Duration duration;
    @Size(min = 1, max = 200)
    private String description;
    private Set<Integer> likes = new HashSet<>();
    private int id;
}
