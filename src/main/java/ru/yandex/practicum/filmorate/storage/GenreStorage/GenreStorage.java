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
import ru.yandex.practicum.filmorate.storage.GetFieldStorage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository("genreBean")
public class GenreStorage implements GetFieldStorage<Genres> {

    private final Logger log = LoggerFactory.getLogger(GenreStorage.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genres getById(int id) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("SELECT * FROM genre WHERE genre_id = ?", id);
        Genres genre;
        if (rowSet.next()) {
            genre = new Genres(rowSet.getInt("genre_id"), rowSet.getString("genre_name"));
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
        ArrayList<Integer> idGenres = new ArrayList<>(jdbcTemplate.queryForList(sqlFilmForList, Integer.class));
        for (Integer id : idGenres) {
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