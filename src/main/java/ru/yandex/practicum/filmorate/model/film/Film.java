package ru.yandex.practicum.filmorate.model.film;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.model.Entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class Film extends Entity<Long> {
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private int rate;
    private final Set<Long> likes = new HashSet<>();
}