package ru.yandex.practicum.filmorate.web.mapper;

import org.mapstruct.Mapper;
import ru.yandex.practicum.filmorate.model.mpa.Mpa;
import ru.yandex.practicum.filmorate.web.dto.MpaDto;

@Mapper
public interface MpaMapper {
    MpaDto mapToMapResponseDto(Mpa mpa);
}
