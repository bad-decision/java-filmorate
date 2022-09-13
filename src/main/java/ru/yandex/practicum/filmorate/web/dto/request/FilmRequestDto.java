package ru.yandex.practicum.filmorate.web.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.yandex.practicum.filmorate.web.dto.GenreDto;
import ru.yandex.practicum.filmorate.web.dto.MpaDto;
import ru.yandex.practicum.filmorate.web.validator.DateIsAfter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class FilmRequestDto {

    private Long id;

    @NotBlank
    private String name;

    @Size(max = 200)
    private String description;

    @DateIsAfter(current = "28.12.1895", message = "Date must be after 28.12.1895")
    @DateTimeFormat( pattern="dd.MM.yyyy")
    private LocalDate releaseDate;

    private Integer duration;

    private Integer rate;

    private MpaDto mpa;

    private Set<Long> likes;

    private Set<GenreDto> genres;
}
