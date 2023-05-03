package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class Film {
    @NotBlank
    private final String name;
    private final LocalDate releaseDate;
    private final Duration duration;
    @Size(min =1,max = 200)
    private final String description;
    private int id;
}
