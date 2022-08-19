package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User findById(Long id) {
        Optional<User> userOpt = userStorage.findById(id);

        if (userOpt.isEmpty())
            throw new NotFoundException("User not found, id=" + id);

        return userOpt.get();
    }

    public List<User> findAll() {
        return userStorage.findAll();
    }

    public User create(User user) {
        return userStorage.add(user);
    }

    public User update(User user) {
        if (!userStorage.existsById(user.getId()))
            throw new NotFoundException("User not found, id=" + user.getId());

        return userStorage.update(user);
    }

    //region Friends
    public List<User> getFriends(Long id) {
        Optional<User> userOpt = userStorage.findById(id);

        if (userOpt.isEmpty())
            throw new NotFoundException("User not found, id=" + id);

        User user = userOpt.get();
        return user.getFriends()
                .stream()
                .map(this::findById)
                .collect(Collectors.toList());
    }

    public User addFriend(Long id, Long friendId) {
        List<User> users = getUsers(List.of(id, friendId));
        User user = users.get(0);
        User friend = users.get(1);

        user.getFriends().add(friendId);
        friend.getFriends().add(id);

        return user;
    }

    public User deleteFriend(Long id, Long friendId) {
        List<User> users = getUsers(List.of(id, friendId));
        User user = users.get(0);
        User friend = users.get(1);

        user.getFriends().remove(friendId);
        friend.getFriends().remove(id);

        return user;
    }

    public List<User> getCommonFriends(Long id, Long otherId) {
        List<User> users = getUsers(List.of(id, otherId));
        User user = users.get(0);
        User otherUser = users.get(1);

        Set<Long> commonFriend = new HashSet<>(user.getFriends());
        commonFriend.retainAll(otherUser.getFriends());

        return commonFriend.stream()
                .map(this::findById)
                .collect(Collectors.toList());
    }
    //endregion

    private List<User> getUsers(List<Long> ids) {
        List<User> users = new ArrayList<>();
        for (Long id: ids) {
            Optional<User> userOpt = userStorage.findById(id);
            if (userOpt.isEmpty())
                throw new NotFoundException("User not found, id=" + id);

            users.add(userOpt.get());
        }
        return users;
    }
}
