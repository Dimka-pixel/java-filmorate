package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
public class Film {
    @NotBlank
    private String name;
    private LocalDate releaseDate;
    private Duration duration;
    @Size(min = 1, max = 200)
    private String description;
    private Set<Integer> likes;
    private Set<Genres> genres;
    private Mpa mpa;
    private int id;

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("film_name", name);
        values.put("release_date", Date.valueOf(releaseDate));
        values.put("description", description);
        values.put("duration", duration.toSeconds());
        values.put("mpa_id", mpa.getId());
        return values;
    }
}
