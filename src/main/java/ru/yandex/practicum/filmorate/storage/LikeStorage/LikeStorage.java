package ru.yandex.practicum.filmorate.storage.LikeStorage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Repository
public class LikeStorage {
    private final JdbcTemplate jdbcTemplate;

    private final Logger log = LoggerFactory.getLogger(LikeStorage.class);


    public LikeStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addLike(int userId, int filmId) {
        String sqlLike = "insert into user_like(user_id, film_id) " +
                "values(?,?)";
        jdbcTemplate.update(sqlLike, userId, filmId);

    }

    public void deleteLike(int filmId, int userId) {
        SqlRowSet rowSetFilm = jdbcTemplate.queryForRowSet("SELECT * FROM film where film_id = ?", filmId);
        SqlRowSet rowSetUser = jdbcTemplate.queryForRowSet("SELECT * FROM users WHERE users_id = ?", userId);
        if (!(rowSetFilm.next())) {
            log.warn("Film c filmId " + filmId + " не найден");
            throw new ValidationException("Film c filmId " + filmId + " не найден", NOT_FOUND);
        } else if (!(rowSetUser.next())) {
            log.warn("Like c userId " + userId + " не найден");
            throw new ValidationException("Like c userId " + userId + " не найден", NOT_FOUND);
        } else {
            String sqlDeleteLike = "DELETE FROM user_like WHERE user_id = ? AND film_id = ?";
            jdbcTemplate.update(sqlDeleteLike, userId, filmId);
        }
    }

    public List<Integer> getTopFilmForLikes() {
        String sqlGetTop = "SELECT film_id " +
                "FROM user_like " +
                "GROUP BY film_id " +
                "ORDER BY count(user_id) DESC " +
                "LIMIT 10";
        ArrayList<Integer> topFilm = new ArrayList<>(jdbcTemplate.queryForList(sqlGetTop, Integer.class));
        return topFilm;
    }


}
