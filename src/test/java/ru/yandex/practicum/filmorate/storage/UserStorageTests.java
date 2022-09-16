package ru.yandex.practicum.filmorate.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UserStorageTests {
    private final UserStorage userStorage;

    public UserStorageTests(@Qualifier("userDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Test
    public void findUserById_mustReturnCorrectUser() {
        Optional<User> userOptional = userStorage.findById(1L);

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user)
                                .hasFieldOrPropertyWithValue("id", 1L)
                                .hasFieldOrPropertyWithValue("name", "User1Name")
                );
    }

    @Test
    public void findAllUsers_mustReturnNotEmptyList() {
        List<User> users = userStorage.findAll();
        assertThat(users).isNotEmpty();
    }

    @Test
    public void existUserById_mustReturnTrue() {
        Assertions.assertTrue(userStorage.existsById(1L));
    }

    @Test
    public void addUser_mustReturnAddedUser() {
        User newUser = new User();
        newUser.setName("NewUserName");
        newUser.setBirthday(LocalDate.of(2021,2,1));
        newUser.setEmail("newuser@user.com");
        newUser.setLogin("newUserLogin");

        int countBefore = userStorage.findAll().size();
        User addedUser = userStorage.add(newUser);
        int countAfter = userStorage.findAll().size();

        Assertions.assertEquals(countAfter, countBefore + 1);
        Assertions.assertNotNull(addedUser.getId());
    }

    @Test
    public void updateUser_mustReturnUpdatedUser() {
        User user = userStorage.findById(1L).get();
        user.setLogin("UserUpdatedLogin");
        user.setBirthday(LocalDate.of(1999, 1, 3));

        userStorage.update(user);
        User updatedUser = userStorage.findById(1L).get();

        Assertions.assertEquals(user.getLogin(), updatedUser.getLogin());
        Assertions.assertEquals(user.getBirthday(), updatedUser.getBirthday());
        Assertions.assertEquals(user.getId(), updatedUser.getId());
    }

    @Test
    public void deleteUserById_mustCorrectDelete() {
        int countBefore = userStorage.findAll().size();
        userStorage.deleteById(1L);
        int countAfter = userStorage.findAll().size();

        Optional<User> userOptional = userStorage.findById(1L);

        assertThat(userOptional).isEmpty();
        Assertions.assertEquals(countAfter, countBefore - 1);
    }

    @Test
    public void addFriend_mustIncreaseFriendCount() {
        int countBefore = userStorage.getFriends(1L).size();
        userStorage.addFriend(1L, 3L);
        int countAfter = userStorage.getFriends(1L).size();

        Assertions.assertEquals(countAfter, countBefore + 1);
    }

    @Test
    public void deleteFriend_mustDecreaseFriendsCount() {
        int countBefore = userStorage.getFriends(2L).size();
        userStorage.deleteFriend(2L, 1L);
        int countAfter = userStorage.getFriends(2L).size();

        Assertions.assertEquals(countAfter, countBefore - 1);
    }

    @Test
    public void getFriends_mustReturnUserFriends() {
        List<User> friends = userStorage.getFriends(2L);
        Assertions.assertEquals(1, friends.size());
    }
}
