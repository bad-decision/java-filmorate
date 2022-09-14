package ru.yandex.practicum.filmorate.model.mpa;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.model.Entity;

@Data
@EqualsAndHashCode(callSuper = true)
public class Mpa extends Entity<Long> {
    private String name;
}
