package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.friendship.Friendship;
import ru.yandex.practicum.filmorate.model.user.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User add(User item) {
        String insertQuery = "INSERT INTO users (name, login, email, birthday) VALUES (?,?,?,?)";
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(insertQuery, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.DATE);
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(item.getName(), item.getLogin(),
                item.getEmail(), Date.valueOf(item.getBirthday())));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        pscf.setReturnGeneratedKeys(true);
        jdbcTemplate.update(psc, keyHolder);

        Long id = (Long) keyHolder.getKey();
        return findById(id).orElse(null);
    }

    @Override
    public User update(User item) {
        String updateQuery = "UPDATE users SET name=?, login=?, email=?, birthday=? WHERE user_id=?";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(updateQuery);
            ps.setString(1, item.getName());
            ps.setString(2, item.getLogin());
            ps.setString(3, item.getEmail());
            ps.setDate(4, Date.valueOf(item.getBirthday()));
            ps.setLong(5, item.getId());
            return ps;
        });
        return findById(item.getId()).orElse(null);
    }

    @Override
    public Optional<User> findById(Long id) {
        String findByIdQuery = "SELECT user_id, name, login, email, birthday FROM users WHERE user_id=?";
        return jdbcTemplate.query(findByIdQuery, new UserMapper(), id)
                .stream()
                .findAny();
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public List<User> findAll() {
        String findAllQuery = "SELECT user_id, name, login, email, birthday FROM users";
        return jdbcTemplate.query(findAllQuery, new UserMapper());
    }

    @Override
    public void deleteById(Long id) {
        String deleteByIdQuery = "DELETE FROM users WHERE user_id=?";
        jdbcTemplate.update(deleteByIdQuery, id);
    }

    @Override
    public User addFriend(Long userId, Long friendId) {
        String checkExistFriendshipQuery = "SELECT from_user_id, to_user_id, is_confirmed FROM friendship WHERE from_user_id=? AND to_user_id=?";
        Optional<Friendship> existFriendship = jdbcTemplate.query(checkExistFriendshipQuery, new FriendshipMapper(), friendId, userId)
                .stream()
                .findAny();

        if (existFriendship.isPresent() && !existFriendship.get().isConfirmed()) {
            String insertLikeQuery = "UPDATE friendship SET is_confirmed=1 WHERE from_user_id=? AND to_user_id=?";
            jdbcTemplate.update(insertLikeQuery, friendId, userId);
        }
        else {
            String insertLikeQuery = "INSERT INTO friendship (from_user_id, to_user_id) VALUES (?,?)";
            jdbcTemplate.update(insertLikeQuery, friendId, userId);
        }

        return findById(userId).orElse(null);
    }

    @Override
    public User deleteFriend(Long userId, Long friendId) {
        String deleteLikeQuery = "DELETE FROM friendship WHERE from_user_id=? AND to_user_id=?";
        jdbcTemplate.update(deleteLikeQuery, friendId, userId);
        return findById(userId).orElse(null);
    }

    @Override
    public List<User> getFriends(Long id) {
        String findFriendsQuery = "SELECT user_id, name, login, email, birthday FROM users WHERE user_id IN " +
                "(SELECT to_user_id FROM friendship WHERE from_user_id=? AND is_confirmed=1 UNION " +
                "SELECT from_user_id FROM friendship WHERE to_user_id=?)";
        return jdbcTemplate.query(findFriendsQuery, new UserMapper(), id, id);
    }
}
