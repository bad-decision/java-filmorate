package ru.yandex.practicum.filmorate.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.service.BaseService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService extends BaseService<User> {

    private final UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage) {
        super(userStorage);
        this.userStorage = userStorage;
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
        for (Long id : ids) {
            Optional<User> userOpt = userStorage.findById(id);
            if (userOpt.isEmpty())
                throw new NotFoundException("User not found, id=" + id);

            users.add(userOpt.get());
        }
        return users;
    }
}
