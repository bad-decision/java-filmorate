package ru.yandex.practicum.filmorate.model.mpa;

import lombok.Data;
import ru.yandex.practicum.filmorate.model.Entity;

@Data
public class Mpa extends Entity<Long> {
    private String name;
}
