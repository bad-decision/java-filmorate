package ru.yandex.practicum.filmorate.service.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.genre.Genre;
import ru.yandex.practicum.filmorate.service.BaseService;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

@Service
public class GenreService extends BaseService<Genre> {
    @Autowired
    public GenreService(GenreStorage genreStorage) {
        super(genreStorage);
    }
}