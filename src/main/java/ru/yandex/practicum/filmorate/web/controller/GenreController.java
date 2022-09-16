package ru.yandex.practicum.filmorate.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.genre.Genre;
import ru.yandex.practicum.filmorate.service.genre.GenreService;
import ru.yandex.practicum.filmorate.web.dto.GenreDto;
import ru.yandex.practicum.filmorate.web.mapper.GenreMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/genres")
@Slf4j
@Validated
public class GenreController {

    private final GenreService genreService;
    private final GenreMapper genreMapper;

    @Autowired
    public GenreController(GenreService genreService, GenreMapper genreMapper) {
        this.genreService = genreService;
        this.genreMapper = genreMapper;
    }

    @GetMapping("/{id}")
    public GenreDto getById(@PathVariable Long id) {
        Genre genre = genreService.findById(id);
        return genreMapper.mapToGenreResponseDto(genre);
    }

    @GetMapping
    public List<GenreDto> getAll() {
        List<Genre> genres = genreService.findAll();
        return genres.stream()
                .map(genreMapper::mapToGenreResponseDto)
                .collect(Collectors.toList());
    }
}
