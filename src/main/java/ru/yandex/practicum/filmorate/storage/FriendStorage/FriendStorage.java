package ru.yandex.practicum.filmorate.storage.FriendStorage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Repository
public class FriendStorage {

    private final Logger log = LoggerFactory.getLogger(FriendStorage.class);
    private final JdbcTemplate jdbcTemplate;

    public FriendStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addFriend(int userId, int friendId) {
        SqlRowSet rowSetUser = jdbcTemplate.queryForRowSet("SELECT * FROM users WHERE users_id = ? ", userId);
        SqlRowSet rowSetFriend = jdbcTemplate.queryForRowSet("SELECT * FROM users WHERE users_id = ? ", friendId);
        if (rowSetUser.next()) {
            if (rowSetFriend.next()) {
                String sqlAddFriend = "INSERT into user_friend(user_id, friend_id) " +
                        "values(?, ?) ";
                jdbcTemplate.update(sqlAddFriend, userId, friendId);
            } else {
                throw new ValidationException("user c ID " + friendId + " не найден", HttpStatus.NOT_FOUND);
            }
        } else {
            throw new ValidationException("user c ID " + userId + " не найден", HttpStatus.NOT_FOUND);
        }
    }

    public void deletedFriend(int userId, int friendId) {
        SqlRowSet rowSetUser = jdbcTemplate.queryForRowSet("SELECT * FROM users WHERE users_id = ?", userId);
        SqlRowSet rowSetFriend = jdbcTemplate.queryForRowSet("SELECT * FROM users WHERE users_id = ?", friendId);
        if (!(rowSetUser.next())) {
            log.warn("User c userId " + userId + " не найден");
            throw new ValidationException("User c userId " + userId + " не найден", NOT_FOUND);
        } else if (!(rowSetFriend.next())) {
            log.warn("Friend c friendId " + friendId + " не найден");
            throw new ValidationException("Friend c friendId " + friendId + " не найден", NOT_FOUND);
        } else {
            String sqlFriendDelete = "DELETE FROM user_friend WHERE friend_id = ? AND user_id = ?";
            jdbcTemplate.update(sqlFriendDelete, friendId, userId);
        }
    }

    public List<Integer> getFriends(int id) {
        List<Integer> friends = new ArrayList<>();
        String sqlGetFriend = "SELECT friend_id " +
                "FROM user_friend " +
                "WHERE user_id = ?";
        friends = jdbcTemplate.queryForList(sqlGetFriend, Integer.class, id);
        return friends;

    }

    public List<Integer> getCommonFriend(int userId, int otherId) {
        List<Integer> commonFriend = new ArrayList<>();
        String sqlGetCommonFriend = "SELECT friend_id " +
                "FROM user_friend " +
                "WHERE user_id = ? " +
                "AND friend_id IN (SELECT friend_id " +
                "FROM user_friend " +
                "WHERE user_id = ?)";
        commonFriend = jdbcTemplate.queryForList(sqlGetCommonFriend, Integer.class, userId, otherId);
        return commonFriend;
    }
}
