package ru.yandex.practicum.filmorate.model.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.model.Entity;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends Entity<Long> {
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    private final Set<Long> friends = new LinkedHashSet<>();

    public String getName() {
        return name.isBlank() ? login : name;
    }
}
