package ru.yandex.practicum.filmorate.service.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.service.BaseService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService extends BaseService<Film> {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    @Value("${film.defaultPopularCount}")
    private Long defaultPopularCount;

    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage, @Qualifier("userDbStorage") UserStorage userStorage) {
        super(filmStorage);
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public List<Film> getPopular(Optional<Long> countOpt) {
        Long count = countOpt.orElseGet(() -> defaultPopularCount);
        return filmStorage.getPopular(count);
//        Long count = countOpt.orElseGet(() -> defaultPopularCount);
//
//        return films.stream().sorted((x, y) -> y.getLikes().size() - x.getLikes().size())
//                .limit(count)
//                .collect(Collectors.toList());
    }

    //region Like
    public Film addLike(Long id, Long userId) {
        Optional<Film> filmOpt = filmStorage.findById(id);

        if (filmOpt.isEmpty())
            throw new NotFoundException("Film not found, id=" + id);

        if (!userStorage.existsById(userId))
            throw new NotFoundException("User not found, id=" + userId);

        return filmStorage.addLike(id, userId);
    }

    public Film deleteLike(Long id, Long userId) {
        Optional<Film> filmOpt = filmStorage.findById(id);

        if (filmOpt.isEmpty())
            throw new NotFoundException("Film not found, id=" + id);

        if (!userStorage.existsById(userId))
            throw new NotFoundException("User not found, id=" + userId);

        Film film = filmOpt.get();
        Set<Long> likes = film.getLikes();

        if (!likes.contains(userId))
            throw new NotFoundException("Like not found, id=" + id + ", userId=" + userId);

        return filmStorage.deleteLike(id, userId);
    }
    //endregion
}
