package ru.yandex.practicum.filmorate.storage.MpaStorage;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.GetFieldStorage;

import java.util.ArrayList;
import java.util.List;

@Repository("MPABean")
public class MpaDbStorage implements GetFieldStorage<Mpa> {
    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Mpa getById(int id) {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT * FROM film_mpa WHERE mpa_id = ? ", id);
        Mpa mpa;
        if (sqlRowSet.next()) {
            mpa = new Mpa(sqlRowSet.getInt("mpa_id"), sqlRowSet.getString("mpa_name"));
        } else {
            throw new ValidationException("Mpa c ID " + id + "не найден", HttpStatus.NOT_FOUND);
        }
        return mpa;
    }

    @Override
    public List<Mpa> getAll() {
        ArrayList<Mpa> allAllMpa = new ArrayList<>();
        String sqlFilmForList = "SELECT mpa_id FROM film_mpa";
        jdbcTemplate.queryForList(sqlFilmForList, Integer.class);
        ArrayList<Integer> IdGenres = new ArrayList<>(jdbcTemplate.queryForList(sqlFilmForList, Integer.class));
        for (Integer id : IdGenres) {
            allAllMpa.add(getById(id));
        }
        return allAllMpa;
    }
}
