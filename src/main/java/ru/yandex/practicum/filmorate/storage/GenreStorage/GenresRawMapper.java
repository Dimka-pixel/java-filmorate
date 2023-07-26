package ru.yandex.practicum.filmorate.storage.GenreStorage;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Genres;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenresRawMapper implements RowMapper<Genres> {

    @Override
    public Genres mapRow(ResultSet rs, int rowNum) throws SQLException {
        Genres genres = new Genres(rs.getInt("genre_id"), rs.getString("genre_name"));

        return genres;
    }
}
