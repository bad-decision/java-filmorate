package ru.yandex.practicum.filmorate.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.service.BaseService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.Optional;
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

        return userStorage.getFriends(id);
    }

    public User addFriend(Long id, Long friendId) {
        if (usersExists(List.of(id, friendId))) {
            userStorage.addFriend(id, friendId);
        }
        return userStorage.findById(id).orElse(null);
    }

    public User deleteFriend(Long id, Long friendId) {
        if (usersExists(List.of(id, friendId))) {
            userStorage.deleteFriend(id, friendId);
        }
        return userStorage.findById(id).orElse(null);
    }

    public List<User> getCommonFriends(Long id, Long otherId) {
        if (usersExists(List.of(id, otherId))) {
            List<User> userFriends = userStorage.getFriends(id);
            List<User> otherUserFriends = userStorage.getFriends(otherId);

            return userFriends.stream()
                    .filter(x -> otherUserFriends.stream().anyMatch(y -> y.getId().equals(x.getId())))
                    .collect(Collectors.toList());
        }
        return null;
    }
    //endregion

    private boolean usersExists(List<Long> ids) {
        for (Long id : ids) {
            Optional<User> userOpt = userStorage.findById(id);
            if (userOpt.isEmpty())
                throw new NotFoundException("User not found, id=" + id);
        }
        return true;
    }
}
