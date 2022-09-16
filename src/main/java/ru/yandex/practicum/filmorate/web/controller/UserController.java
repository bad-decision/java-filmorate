package ru.yandex.practicum.filmorate.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.web.dto.request.UserRestCommand;
import ru.yandex.practicum.filmorate.web.dto.response.UserRestView;
import ru.yandex.practicum.filmorate.web.mapper.UserMapper;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Slf4j
@Validated
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/{id}")
    public UserRestView getById(@PathVariable Long id) {
        User user = userService.findById(id);
        return userMapper.mapToUserRestView(user);
    }

    @GetMapping
    public List<UserRestView> getAll() {
        List<User> users = userService.findAll();
        return users.stream()
                .map(userMapper::mapToUserRestView)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/friends")
    public List<UserRestView> getFriends(@PathVariable Long id) {
        List<User> friends = userService.getFriends(id);
        return friends.stream()
                .map(userMapper::mapToUserRestView)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<UserRestView> getCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        List<User> friends = userService.getCommonFriends(id, otherId);
        return friends.stream()
                .map(userMapper::mapToUserRestView)
                .collect(Collectors.toList());
    }

    @PostMapping
    public UserRestView create(@Valid @RequestBody UserRestCommand userDto) {
        User user = userMapper.mapToUser(userDto);
        user = userService.create(user);
        log.info("{} successfully added", user);
        return userMapper.mapToUserRestView(user);
    }

    @PutMapping
    public UserRestView update(@Valid @RequestBody UserRestCommand userDto) {
        User user = userMapper.mapToUser(userDto);
        user = userService.update(user);
        log.info("{} successfully updated", user);
        return userMapper.mapToUserRestView(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public UserRestView addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        User user = userService.addFriend(id, friendId);
        log.info("{} successfully add friend {}", user, friendId);
        return userMapper.mapToUserRestView(user);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public UserRestView deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        User user = userService.deleteFriend(id, friendId);
        log.info("{} successfully delete friend {}", user, friendId);
        return userMapper.mapToUserRestView(user);
    }
}
