package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {

    private Long id;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Pattern(regexp = "\\S+", message = "Логин не должен содержать пробелы")
    private String login;

    private String name;

    @PastOrPresent
    @DateTimeFormat( pattern="dd.MM.yyyy")
    private LocalDate birthday;

    public String getName() {
        return name.isBlank() ? login : name;
    }
}
