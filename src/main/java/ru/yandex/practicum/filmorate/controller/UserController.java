package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/users")
@Slf4j
@Validated
public class UserController {

    private static Long userId = 0L;
    private final Map<Long, User> users = new HashMap<>();

    @GetMapping
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        user.setId(++userId);
        users.put(user.getId(), user);
        log.info("{} successfully added", user);
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        if (!users.containsKey(user.getId())) {
            log.error("{} not found", user);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        users.put(user.getId(), user);
        log.info("{} successfully updated", user);
        return user;
    }
}
