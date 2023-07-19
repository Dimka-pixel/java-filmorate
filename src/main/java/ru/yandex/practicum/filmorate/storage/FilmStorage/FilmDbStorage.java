package ru.yandex.practicum.filmorate.storage.FilmStorage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genres;
import ru.yandex.practicum.filmorate.storage.CrudStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage.GenresRawMapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Repository("FilmDb")
public class FilmDbStorage implements CrudStorage<Film> {

    private final Logger log = LoggerFactory.getLogger(FilmDbStorage.class);

    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int addObject(Film film) {
        long filmId;
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("film")
                .usingGeneratedKeyColumns("film_id");
        filmId = simpleJdbcInsert.executeAndReturnKey(film.toMap()).longValue();
        log.warn("duration:" + film.toMap().get("duration"));
        String sqlQueryGenre = "insert into film_genre(genre_id, film_id) " +
                "values(?, ?)";
        if (film.getGenres() != null) {

            List<Genres> genres = new ArrayList<>(film.getGenres());
            jdbcTemplate.batchUpdate(sqlQueryGenre, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1, genres.get(i).getId());
                    ps.setLong(2, filmId);
                }

                @Override
                public int getBatchSize() {
                    return genres.size();
                }
            });
        }

        return (int) filmId;
    }

    @Override
    public void updateObject(Film film) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("SELECT * FROM film where film_id = ?", film.getId());
        if (rowSet.next()) {
            String sqlQuery = "update film set " +
                    "film_name = ?, " +
                    "release_date = ?, " +
                    "description = ?, " +
                    "duration = ? " +
                    "where film_id = ?";

            jdbcTemplate.update(sqlQuery,
                    film.getName(),
                    film.getReleaseDate(),
                    film.getDescription(),
                    film.getDuration(),
                    film.getId());
            if (film.getMpa() != null) {
                String sqlQueryMpa = "update film set " +
                        "mpa_id = ? " +
                        "where film_id = ?";
                jdbcTemplate.update(sqlQueryMpa,
                        film.getMpa().getId(),
                        film.getId());
            }

            if (film.getGenres() != null) {
                String sqlDeleteGenres = "delete from film_genre where film_id = ?";
                jdbcTemplate.update(sqlDeleteGenres, film.getId());
                for (Genres genres : film.getGenres()) {
                    String sqlQueryGenre = "insert into film_genre(genre_id, film_id) " +
                            "values(?, ?)";
                    jdbcTemplate.update(sqlQueryGenre,
                            genres.getId(),
                            film.getId());
                }
            }
        } else {
            throw new ValidationException("Фильм с ID " + film.getId() + " не найден", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Film getObjectById(int id) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("SELECT * FROM film WHERE film_id = ?", id);
        if (rowSet.next()) {
            String sqlSelect = "SELECT f.film_id, " +
                    "f.film_name, " +
                    "f.release_date, " +
                    "f.description, " +
                    "f.duration, " +
                    "m.mpa_name, " +
                    "m.mpa_id " +
                    "FROM film AS f " +
                    "LEFT OUTER JOIN film_mpa AS m ON f.mpa_id = m.mpa_id " +
                    "WHERE f.film_id = ?";
            Film film = jdbcTemplate.queryForObject(sqlSelect, new FilmRawMapper(), id);
            String sqlGenres = "SELECT g.genre_name, " +
                    "g.genre_id " +
                    "FROM film_genre " +
                    "LEFT OUTER JOIN genre AS g ON film_genre.genre_id = g.genre_id " +
                    "WHERE film_genre.film_id = " + id;
            ArrayList<Genres> genres = new ArrayList<>(jdbcTemplate.query(sqlGenres, new GenresRawMapper()));
            film.setGenres(new LinkedHashSet<>(genres));
            return film;
        } else {
            throw new ValidationException("Film с ID " + id + " не найден", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Film> getAllObjects() {
        ArrayList<Film> allFilm = new ArrayList<>();
        String sqlFilmForList = "SELECT film_id FROM film";
        ArrayList<Integer> idFilm = new ArrayList<>(jdbcTemplate.queryForList(sqlFilmForList, Integer.class));
        idFilm.sort(Integer::compareTo);
        for (Integer id : idFilm) {
            allFilm.add(getObjectById(id));
        }
        return allFilm;
    }

    @Override
    public void deleteObject(int id) {
        String sqlDelete = "delete from film where id = ?";
        jdbcTemplate.update(sqlDelete, id);
    }
}
