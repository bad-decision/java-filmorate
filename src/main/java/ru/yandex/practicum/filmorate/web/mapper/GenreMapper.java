package ru.yandex.practicum.filmorate.web.mapper;

import org.mapstruct.Mapper;
import ru.yandex.practicum.filmorate.model.genre.Genre;
import ru.yandex.practicum.filmorate.web.dto.GenreDto;

@Mapper
public interface GenreMapper {
    GenreDto mapToGenreResponseDto(Genre genre);
}
