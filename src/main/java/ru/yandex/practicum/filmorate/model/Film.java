package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.time.Duration;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class Film {
    @NotBlank
    private final String name;
    private final LocalDate releaseDate;
    private final Duration duration;
    private final String description;
    private int id;
}
