package ru.yandex.practicum.filmorate.storage.UserStorage;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRawMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("users_id"));
        user.setBirthday(rs.getDate("birthday").toLocalDate());
        user.setName(rs.getString("users_name"));
        user.setLogin(rs.getString("user_login"));
        user.setEmail(rs.getString("email"));
        return user;
    }
}
