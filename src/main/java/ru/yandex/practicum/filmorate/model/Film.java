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
    final private String name;
    final private LocalDate releaseDate;
    final private Duration duration;
    @Max(200)
    final private String description;
    private int id;
}
