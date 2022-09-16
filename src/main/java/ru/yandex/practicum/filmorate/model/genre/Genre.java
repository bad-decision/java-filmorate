package ru.yandex.practicum.filmorate.model.genre;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.model.Entity;

@Data
@EqualsAndHashCode(callSuper = true)
public class Genre extends Entity<Long> {
    private Long id;
    private String name;
}
