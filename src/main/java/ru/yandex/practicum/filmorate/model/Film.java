package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.yandex.practicum.filmorate.util.DateIsAfter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class Film {

    private Long id;

    @NotBlank
    private String name;

    @Size(max = 200)
    private String description;

    @DateIsAfter(current = "28.12.1895", message = "Дата должна быть позже 28.12.1895")
    @DateTimeFormat( pattern="dd.MM.yyyy")
    private LocalDate releaseDate;

    @Positive
    private int duration;
}
