package ru.yandex.practicum.filmorate.web.mapper;

import org.mapstruct.Mapper;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.web.dto.request.UserRequestDto;
import ru.yandex.practicum.filmorate.web.dto.response.UserResponseDto;

@Mapper
public interface UserMapper {
    User mapToUser(UserRequestDto dto);
    UserResponseDto mapToUserResponseDto(User user);
}
