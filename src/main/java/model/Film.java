package model;

import java.time.Duration;
import java.time.LocalDate;

@lombok.Data
@lombok.AllArgsConstructor
public class Film {
    final private int ID;
    final private String name;
    final private LocalDate releaseDate;
    final private Duration duration;
    private String description;
}
