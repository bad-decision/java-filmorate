package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.InMemoryStorage;

import java.util.List;

@Component
public class InMemoryUserStorage extends InMemoryStorage<User> implements UserStorage {
    @Override
    public User addFriend(Long id, Long friendId) {
        return null;
    }

    @Override
    public User deleteFriend(Long id, Long friendId) {
        return null;
    }

    @Override
    public List<User> getFriends(Long id) {
        return null;
    }
}
