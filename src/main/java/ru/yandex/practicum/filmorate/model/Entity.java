package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Entity<K> {
    private K id;
}
