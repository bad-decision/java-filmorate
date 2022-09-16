package ru.yandex.practicum.filmorate.model.film;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.model.Entity;
import ru.yandex.practicum.filmorate.model.genre.Genre;
import ru.yandex.practicum.filmorate.model.mpa.Mpa;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class Film extends Entity<Long> {
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    private Integer rate;
    private Mpa mpa;

    private final Set<Genre> genres = new LinkedHashSet<>();
    private final Set<Long> likes = new HashSet<>();
}
