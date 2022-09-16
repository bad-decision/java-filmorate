package ru.yandex.practicum.filmorate.web.mapper;

import org.mapstruct.Mapper;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.web.dto.request.UserRestCommand;
import ru.yandex.practicum.filmorate.web.dto.response.UserRestView;

@Mapper
public interface UserMapper {
    User mapToUser(UserRestCommand dto);
    UserRestView mapToUserRestView(User user);
}
