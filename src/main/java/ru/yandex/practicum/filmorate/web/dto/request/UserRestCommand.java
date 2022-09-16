package ru.yandex.practicum.filmorate.web.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class UserRestCommand {

    private Long id;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Pattern(regexp = "\\S+", message = "Login must not contain spaces")
    private String login;

    private String name;

    @PastOrPresent
    @DateTimeFormat( pattern="dd.MM.yyyy")
    private LocalDate birthday;

    private Set<Long> friends;
}
