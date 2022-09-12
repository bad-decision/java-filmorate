package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.List;

public interface FilmStorage extends Storage<Film, Long> {
    Film addLike(Long filmId, Long userId);
    Film deleteLike(Long filmId, Long userId);
    List<Film> getPopular(Long count);
}
