package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends Entity<Long> {
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    private final Set<Long> friends;

    public User() {
        this.friends = new HashSet<>();
    }

    public String getName() {
        return name.isBlank() ? login : name;
    }
}
