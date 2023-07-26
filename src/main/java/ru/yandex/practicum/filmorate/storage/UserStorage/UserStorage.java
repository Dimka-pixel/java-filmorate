package ru.yandex.practicum.filmorate.storage.UserStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.CrudStorage;

import java.util.ArrayList;
import java.util.List;

@Repository("UserDb")
public class UserStorage implements CrudStorage<User> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int addObject(User user) {
        long userId;
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("users_id");
        userId = simpleJdbcInsert.executeAndReturnKey(user.toMap()).longValue();

        String sqlQueryFriend = "insert into user_friend(user_id, friend_id)" +
                "values(?,?)";

        return (int) userId;
    }

    @Override
    public void updateObject(User user) {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT * FROM users WHERE users_id = ?", user.getId());
        if (sqlRowSet.next()) {
            String sqlUpdateUser = "UPDATE users set " +
                    "email = ?, " +
                    "user_login = ?, " +
                    "birthday = ?, " +
                    "users_name = ? " +
                    "WHERE users_id = ?";
            jdbcTemplate.update(sqlUpdateUser,
                    user.getEmail(),
                    user.getLogin(),
                    user.getBirthday(),
                    user.getName(),
                    user.getId());
        } else {
            throw new ValidationException("user c ID " + user.getId() + " не найден", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public User getObjectById(int id) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("SELECT * FROM users WHERE users_id = ?", id);
        if (rowSet.next()) {
            String sqlSelect = "SELECT users_id, " +
                    "email, " +
                    "user_login, " +
                    "birthday, " +
                    "users_name " +
                    "FROM users " +
                    "WHERE users.users_id = ?";
            User user = jdbcTemplate.queryForObject(sqlSelect, new UserRawMapper(), id);
            return user;
        } else {
            throw new ValidationException("User с ID " + id + " не найден", HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public List<User> getAllObjects() {
        ArrayList<User> allUser = new ArrayList<>();
        String sqlUserForList = "SELECT users_id FROM users";
        ArrayList<Integer> idUser = new ArrayList<>(jdbcTemplate.queryForList(sqlUserForList, Integer.class));
        idUser.sort(Integer::compareTo);
        for (Integer id : idUser) {
            allUser.add(getObjectById(id));
        }
        return allUser;
    }

    @Override
    public void deleteObject(int id) {

    }

}
