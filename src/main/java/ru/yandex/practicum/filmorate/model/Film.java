package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

@Data
public class Film {
    @NotBlank
    private final String name;
    @NonNull
    private final LocalDate releaseDate;
    @NonNull
    private final Duration duration;
    @Size(min = 1, max = 200)
    private final String description;

    private Set<Integer> likes = new TreeSet<>();

    private int id;
}
