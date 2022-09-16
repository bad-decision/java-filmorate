package ru.yandex.practicum.filmorate.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.web.dto.request.FilmRestCommand;
import ru.yandex.practicum.filmorate.web.dto.response.FilmRestView;
import ru.yandex.practicum.filmorate.web.mapper.FilmMapper;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/films")
@Slf4j
@Validated
public class FilmController {

    private final FilmService filmService;
    private final FilmMapper filmMapper;

    @Autowired
    public FilmController(FilmService filmService, FilmMapper filmMapper) {
        this.filmService = filmService;
        this.filmMapper = filmMapper;
    }

    @GetMapping("/{id}")
    public FilmRestView getById(@PathVariable Long id) {
        Film film = filmService.findById(id);
        return filmMapper.mapToFilmRestView(film);
    }

    @GetMapping
    public List<FilmRestView> getAll() {
        List<Film> films = filmService.findAll();
        return films.stream()
                .map(filmMapper::mapToFilmRestView)
                .collect(Collectors.toList());
    }

    @GetMapping("/popular")
    public List<FilmRestView> getPopular(@RequestParam Optional<Long> count) {
        List<Film> films = filmService.getPopular(count);
        return films.stream()
                .map(filmMapper::mapToFilmRestView)
                .collect(Collectors.toList());
    }

    @PostMapping
    public FilmRestView create(@Valid @RequestBody FilmRestCommand filmDto) {
        Film film = filmMapper.mapToFilm(filmDto);
        film = filmService.create(film);
        log.info("{} successfully added", film);
        return filmMapper.mapToFilmRestView(film);
    }

    @PutMapping
    public FilmRestView update(@Valid @RequestBody FilmRestCommand filmDto) {
        Film film = filmMapper.mapToFilm(filmDto);
        film = filmService.update(film);
        log.info("{} successfully updated", film);
        return filmMapper.mapToFilmRestView(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public FilmRestView addLike(@PathVariable Long id, @PathVariable Long userId) {
        Film film = filmService.addLike(id, userId);
        log.info("{} successfully add like", film);
        return filmMapper.mapToFilmRestView(film);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public FilmRestView deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        Film film = filmService.deleteLike(id, userId);
        log.info("{} successfully delete like", film);
        return filmMapper.mapToFilmRestView(film);
    }
}
