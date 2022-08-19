package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmStorage filmStorage;
    @Value("${film.defaultPopularCount}")
    private Long defaultPopularCount;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film findById(Long id) {
        Optional<Film> filmOpt = filmStorage.findById(id);

        if (filmOpt.isEmpty())
            throw new NotFoundException("Film not found, id=" + id);

        return filmOpt.get();
    }

    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film create(Film film) {
        return filmStorage.add(film);
    }

    public Film update(Film film) {
        if (!filmStorage.existsById(film.getId()))
            throw new NotFoundException("Film not found, id=" + film.getId());

        return filmStorage.update(film);
    }

    public List<Film> getPopular(Optional<Long> countOpt) {
        List<Film> films = filmStorage.findAll();
        Long count = countOpt.orElseGet(() -> defaultPopularCount);

        return films.stream().sorted((x, y) -> y.getLikes().size() - x.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }

    //region Like
    public Film addLike(Long id, Long userId) {
        Optional<Film> filmOpt = filmStorage.findById(id);

        if (filmOpt.isEmpty())
            throw new NotFoundException("Film not found, id=" + id);

        Film film = filmOpt.get();
        film.getLikes().add(userId);
        return film;
    }

    public Film deleteLike(Long id, Long userId) {
        Optional<Film> filmOpt = filmStorage.findById(id);

        if (filmOpt.isEmpty())
            throw new NotFoundException("Film not found, id=" + id);

        Film film = filmOpt.get();
        Set<Long> likes = film.getLikes();
        if (!likes.contains(userId))
            throw new NotFoundException("Like not found, id=" + id + ", userId=" + userId);

        likes.remove(userId);
        return film;
    }
    //endregion
}
