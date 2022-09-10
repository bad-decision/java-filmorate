package ru.yandex.practicum.filmorate.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.web.dto.response.FilmResponseDto;

@Component
public class FilmToFilmResponseDTO implements Converter<Film, FilmResponseDto> {

    @Override
    public FilmResponseDto convert(Film film) {
        FilmResponseDto filmDto = new FilmResponseDto();
        filmDto.setId(film.getId());
        filmDto.setName(film.getName());
        filmDto.setDescription(film.getDescription());
        filmDto.setLikes(film.getLikes());
        filmDto.setDuration(film.getDuration());
        filmDto.setRate(film.getRate());
        filmDto.setReleaseDate(film.getReleaseDate());
        return filmDto;
    }
}