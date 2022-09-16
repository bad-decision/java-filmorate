package ru.yandex.practicum.filmorate.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.mpa.Mpa;
import ru.yandex.practicum.filmorate.service.mpa.MpaService;
import ru.yandex.practicum.filmorate.web.dto.MpaDto;
import ru.yandex.practicum.filmorate.web.mapper.MpaMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mpa")
@Slf4j
@Validated
public class MpaController {

    private final MpaService mpaService;
    private final MpaMapper mpaMapper;

    @Autowired
    public MpaController(MpaService mpaService, MpaMapper mpaMapper) {
        this.mpaService = mpaService;
        this.mpaMapper = mpaMapper;
    }

    @GetMapping("/{id}")
    public MpaDto getById(@PathVariable Long id) {
        Mpa mpa = mpaService.findById(id);
        return mpaMapper.mapToMapResponseDto(mpa);
    }

    @GetMapping
    public List<MpaDto> getAll() {
        List<Mpa> mpa = mpaService.findAll();
        return mpa.stream()
                .map(mpaMapper::mapToMapResponseDto)
                .collect(Collectors.toList());
    }
}
