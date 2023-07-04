package ru.yandex.practicum.filmorate.storage.FilmStorage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genres;
import ru.yandex.practicum.filmorate.storage.GenreStorage.GenresRawMapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Component("FilmDb")
@Primary
public class FilmDbStorage implements FilmStorage {

    private final Logger log = LoggerFactory.getLogger(FilmDbStorage.class);

    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate template) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addLike(int userId, int filmId) {
        String sqlLike = "insert into user_like(user_id, film_id) " +
                "values(?,?)";
        jdbcTemplate.update(sqlLike, userId, filmId);

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

        if (film.getLikes() != null) {

            String sqlQueryLikes = "insert into user_like(user_id, film_id) " +
                    "values(?,?)";
            List<Integer> likes = new ArrayList<>(film.getLikes());
            jdbcTemplate.batchUpdate(sqlQueryLikes, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1, likes.get(i));
                    ps.setLong(2, filmId);
                }

                @Override
                public int getBatchSize() {
                    return likes.size();
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
            if (film.getLikes() != null) {
                for (Integer userId : film.getLikes()) {
                    String sqlDeleteLikes = "delete from film_genre where film_id = ?";
                    jdbcTemplate.update(sqlDeleteLikes, film.getId());
                    String sqlQueryLikes = "insert into user_like(film_id, user_id)" +
                            "values(?,?)";
                    jdbcTemplate.update(sqlQueryLikes,
                            film.getId(),
                            userId);

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
                    "FROM film AS f " +
                    "LEFT OUTER JOIN film_mpa AS m ON f.mpa_id = m.mpa_id " +
                    "WHERE f.film_id = ?";
            Film film = jdbcTemplate.queryForObject(sqlSelect, new FilmRawMapper(), id);
            String sqlGenres = "SELECT g.genre_name " +
                    "FROM film_genre " +
                    "LEFT OUTER JOIN genre AS g ON film_genre.genre_id = g.genre_id " +
                    "WHERE film_genre.film_id = " + id;
            ArrayList<Genres> genres = new ArrayList<>(jdbcTemplate.query(sqlGenres, new GenresRawMapper()));
            genres.sort(new Comparator<Genres>() {
                @Override
                public int compare(Genres genre1, Genres genre2) {
                    return genre1.getId() - genre2.getId();
                }
            });
            film.setGenres(new HashSet<>(genres));
            String sqlLike = "SELECT user_id " +
                    "FROM user_like " +
                    "WHERE film_id = ?";
            Set<Integer> like = new HashSet<>(jdbcTemplate.queryForList(sqlLike, Integer.class, id));
            film.setLikes(like);
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
