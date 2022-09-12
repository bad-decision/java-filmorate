package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryStorage;

import java.util.List;

@Component
public class InMemoryFilmStorage extends InMemoryStorage<Film> implements FilmStorage {
    @Override
    public Film addLike(Long filmId, Long userId) {
        return null;
    }

    @Override
    public Film deleteLike(Long filmId, Long userId) {
        return null;
    }

    @Override
    public List<Film> getPopular(Long count) {
        return null;
    }
}
