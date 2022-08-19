package ru.yandex.practicum.filmorate.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.web.dto.response.UserResponseDto;

@Component
public class UserToUserResponseDTO implements Converter<User, UserResponseDto> {

    @Override
    public UserResponseDto convert(User user) {
        UserResponseDto userDto = new UserResponseDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setBirthday(user.getBirthday());
        userDto.setEmail(user.getEmail());
        userDto.setFriends(user.getFriends());
        userDto.setLogin(user.getLogin());
        return userDto;
    }
}