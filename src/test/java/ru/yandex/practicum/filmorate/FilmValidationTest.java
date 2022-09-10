package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.web.dto.request.FilmRequestDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

@SpringBootTest
public class FilmValidationTest {

    private final Validator validator;
    private FilmRequestDto filmDto;

    @Autowired
    public FilmValidationTest(Validator validator) {
        this.validator = validator;
    }

    @BeforeEach
    private void prepareCorrectFilm() {
        filmDto = new FilmRequestDto();
        filmDto.setDuration(100);
        filmDto.setName("Never give up");
        filmDto.setRate(2);
        filmDto.setReleaseDate(LocalDate.of(1895, 12, 29));
    }

    @Test
    public void validateIncorrectReleaseDate_mustReturnConstraint() {
        filmDto.setReleaseDate(LocalDate.of(1895, 12, 28));

        Set<ConstraintViolation<FilmRequestDto>> constraintViolations = validator.validate(filmDto);
        Assertions.assertEquals(1, constraintViolations.size());
        Assertions.assertEquals("Date must be after 28.12.1895", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void validateCorrectReleaseDate_mustNotReturnConstraint() {
        Set<ConstraintViolation<FilmRequestDto>> constraintViolations = validator.validate(filmDto);
        Assertions.assertEquals(0, constraintViolations.size());
    }
}
