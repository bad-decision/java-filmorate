package ru.yandex.practicum.filmorate.web.mapper;

import org.mapstruct.Mapper;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.web.dto.request.FilmRestCommand;
import ru.yandex.practicum.filmorate.web.dto.response.FilmRestView;

@Mapper
public interface FilmMapper {
    Film mapToFilm(FilmRestCommand dto);
    FilmRestView mapToFilmRestView(Film film);
}
