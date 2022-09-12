package ru.yandex.practicum.filmorate.model.genre;

import lombok.Data;
import ru.yandex.practicum.filmorate.model.Entity;

@Data
public class Genre extends Entity<Long> {
    private Long id;
    private String name;
}
