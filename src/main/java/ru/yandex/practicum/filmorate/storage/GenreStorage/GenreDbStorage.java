package ru.yandex.practicum.filmorate.storage.GenreStorage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Genres;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class GenreDbStorage implements GenreStorage {

    private final Logger log = LoggerFactory.getLogger(GenreDbStorage.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genres getById(int id) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("SELECT * FROM genre WHERE genre_id = ?", id);
        Genres genre;
        if (rowSet.next()) {
            genre = Genres.valueOf(rowSet.getString("genre_name"));
        } else {
            throw new ValidationException("Genre c ID" + id + "не найден", HttpStatus.NOT_FOUND);
        }

        return genre;
    }

    @Override
    public List<Genres> getAll() {
        ArrayList<Genres> allGenres = new ArrayList<>();
        String sqlFilmForList = "SELECT genre_id FROM genre";
        jdbcTemplate.queryForList(sqlFilmForList, Integer.class);
        ArrayList<Integer> IdGenres = new ArrayList<>(jdbcTemplate.queryForList(sqlFilmForList, Integer.class));
        for (Integer id : IdGenres) {
            allGenres.add(getById(id));
        }
        allGenres.sort(new Comparator<Genres>() {
            @Override
            public int compare(Genres lhs, Genres rhs) {
                return lhs.getId() - rhs.getId();
            }
        });

        return allGenres;
    }

}
