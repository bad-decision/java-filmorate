package ru.yandex.practicum.filmorate.web.mapper;

import org.mapstruct.Mapper;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.web.dto.request.FilmRequestDto;
import ru.yandex.practicum.filmorate.web.dto.response.FilmResponseDto;

@Mapper
public interface FilmMapper {
    Film mapToFilm(FilmRequestDto dto);
    FilmResponseDto mapToFilmResponseDto(Film film);
}
