package ru.yandex.practicum.filmorate.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.web.dto.request.FilmRequestDto;
import ru.yandex.practicum.filmorate.web.dto.response.FilmResponseDto;
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
    private final ConversionService conversionService;
    private final FilmMapper filmMapper;

    @Autowired
    public FilmController(FilmService filmService, ConversionService conversionService, FilmMapper filmMapper) {
        this.filmService = filmService;
        this.conversionService = conversionService;
        this.filmMapper = filmMapper;
    }

    @GetMapping("/{id}")
    public FilmResponseDto getById(@PathVariable Long id) {
        Film film = filmService.findById(id);
        return conversionService.convert(film, FilmResponseDto.class);
    }

    @GetMapping
    public List<FilmResponseDto> getAll() {
        List<Film> films = filmService.findAll();
        return films.stream()
                .map(film -> conversionService.convert(film, FilmResponseDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/popular")
    public List<FilmResponseDto> getPopular(@RequestParam Optional<Long> count) {
        List<Film> films = filmService.getPopular(count);
        return films.stream()
                .map(film -> conversionService.convert(film, FilmResponseDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping
    public FilmResponseDto create(@Valid @RequestBody FilmRequestDto filmDto) {
        Film film = filmMapper.mapToFilm(filmDto);
        film = filmService.create(film);
        log.info("{} successfully added", film);
        return conversionService.convert(film, FilmResponseDto.class);
    }

    @PutMapping
    public FilmResponseDto update(@Valid @RequestBody FilmRequestDto filmDto) {
        Film film = filmMapper.mapToFilm(filmDto);
        film = filmService.update(film);
        log.info("{} successfully updated", film);
        return conversionService.convert(film, FilmResponseDto.class);
    }

    @PutMapping("/{id}/like/{userId}")
    public FilmResponseDto addLike(@PathVariable Long id, @PathVariable Long userId) {
        Film film = filmService.addLike(id, userId);
        log.info("{} successfully add like", film);
        return conversionService.convert(film, FilmResponseDto.class);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public FilmResponseDto deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        Film film = filmService.deleteLike(id, userId);
        log.info("{} successfully delete like", film);
        return conversionService.convert(film, FilmResponseDto.class);
    }
}
