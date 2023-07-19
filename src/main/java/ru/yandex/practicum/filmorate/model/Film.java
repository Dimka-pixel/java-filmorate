package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

@Data
public class Film {
    @NotBlank
    private String name;
    private LocalDate releaseDate;
    @Positive
    @NotNull
    private int duration;
    @Size(min = 1, max = 200)
    private String description;
    private LinkedHashSet<Genres> genres;
    private Mpa mpa;
    private int id;

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("film_name", name);
        values.put("release_date", Date.valueOf(releaseDate));
        values.put("description", description);
        values.put("duration", duration);
        values.put("mpa_id", mpa.getId());
        return values;
    }
}
