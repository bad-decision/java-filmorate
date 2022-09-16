package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.List;

public interface UserStorage extends Storage<User, Long> {
    User addFriend(Long id, Long friendId);
    User deleteFriend(Long id, Long friendId);
    List<User> getFriends(Long id);
}
