package ru.yandex.practicum.filmorate.model.like;

import lombok.Data;

@Data
public class Like {
    private Long userId;
    private Long filmId;
}
