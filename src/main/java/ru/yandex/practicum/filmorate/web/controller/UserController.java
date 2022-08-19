package ru.yandex.practicum.filmorate.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.web.dto.request.UserRequestDto;
import ru.yandex.practicum.filmorate.web.dto.response.UserResponseDto;
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
    private final ConversionService conversionService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, ConversionService conversionService, UserMapper userMapper) {
        this.userService = userService;
        this.conversionService = conversionService;
        this.userMapper = userMapper;
    }

    @GetMapping("/{id}")
    public UserResponseDto getById(@PathVariable Long id) {
        User user = userService.findById(id);
        return conversionService.convert(user, UserResponseDto.class);
    }

    @GetMapping
    public List<UserResponseDto> getAll() {
        List<User> users = userService.findAll();
        return users.stream()
                .map(user -> conversionService.convert(user, UserResponseDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/friends")
    public List<UserResponseDto> getFriends(@PathVariable Long id) {
        List<User> friends = userService.getFriends(id);
        return friends.stream()
                .map(friend -> conversionService.convert(friend, UserResponseDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<UserResponseDto> getCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        List<User> friends = userService.getCommonFriends(id, otherId);
        return friends.stream()
                .map(friend -> conversionService.convert(friend, UserResponseDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping
    public UserResponseDto create(@Valid @RequestBody UserRequestDto userDto) {
        User user = userMapper.mapToUser(userDto);
        user = userService.create(user);
        log.info("{} successfully added", user);
        return conversionService.convert(user, UserResponseDto.class);
    }

    @PutMapping
    public UserResponseDto update(@Valid @RequestBody UserRequestDto userDto) {
        User user = userMapper.mapToUser(userDto);
        user = userService.update(user);
        log.info("{} successfully updated", user);
        return conversionService.convert(user, UserResponseDto.class);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public UserResponseDto addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        User user = userService.addFriend(id, friendId);
        log.info("{} successfully add friend {}", user, friendId);
        return conversionService.convert(user, UserResponseDto.class);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public UserResponseDto deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        User user = userService.deleteFriend(id, friendId);
        log.info("{} successfully delete friend {}", user, friendId);
        return conversionService.convert(user, UserResponseDto.class);
    }
}
