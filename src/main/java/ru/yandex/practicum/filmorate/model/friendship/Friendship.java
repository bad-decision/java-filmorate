package ru.yandex.practicum.filmorate.model.friendship;

import lombok.Data;

@Data
public class Friendship {
    private Long fromUserId;
    private Long toUserId;
    private boolean isConfirmed;
}
