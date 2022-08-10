package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

@SpringBootTest
public class FilmValidationTest {

    private final Validator validator;
    private Film film;

    @Autowired
    public FilmValidationTest(Validator validator) {
        this.validator = validator;
    }

    @BeforeEach
    private void prepareCorrectFilm() {
        film = new Film();
        film.setDuration(100);
        film.setName("Never give up");
        film.setReleaseDate(LocalDate.of(1895, 12, 29));
    }

    @Test
    public void validateIncorrectReleaseDate_mustReturnConstraint() {
        film.setReleaseDate(LocalDate.of(1895, 12, 28));

        Set<ConstraintViolation<Film>> constraintViolations = validator.validate(film);
        Assertions.assertEquals(1, constraintViolations.size());
        Assertions.assertEquals("Дата должна быть позже 28.12.1895", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void validateCorrectReleaseDate_mustNotReturnConstraint() {
        Set<ConstraintViolation<Film>> constraintViolations = validator.validate(film);
        Assertions.assertEquals(0, constraintViolations.size());
    }
}
