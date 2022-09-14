package ru.yandex.practicum.filmorate.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class FilmStorageTests {
    private final FilmStorage filmStorage;
    private final MpaStorage mpaStorage;

    public FilmStorageTests(@Qualifier("filmDbStorage") FilmStorage filmStorage, @Qualifier("mpaDbStorage") MpaStorage mpaStorage) {
        this.filmStorage = filmStorage;
        this.mpaStorage = mpaStorage;
    }

    @Test
    public void findFilmById_mustReturnCorrectFilm() {

        Optional<Film> filmOptional = filmStorage.findById(1L);

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film)
                                .hasFieldOrPropertyWithValue("id", 1L)
                                .hasFieldOrPropertyWithValue("name", "Film1Name")
                );
    }

    @Test
    public void findAllFilms_mustReturnCorrectFilm() {
        List<Film> films = filmStorage.findAll();
        assertThat(films).isNotEmpty();
    }

    @Test
    public void existFilmById_mustReturnTrue() {
        Assertions.assertTrue(filmStorage.existsById(1L));
    }

    @Test
    public void addFilm_mustReturnAddedFilm() {
        Film newFilm = new Film();
        newFilm.setName("Film3Name");
        newFilm.setDescription("Film3Description");
        newFilm.setDuration(200);
        newFilm.setRate(3);
        newFilm.setReleaseDate(LocalDate.of(2022, 1, 1));
        newFilm.setMpa(mpaStorage.findById(1L).get());

        int countBefore = filmStorage.findAll().size();
        Film addedFilm = filmStorage.add(newFilm);
        int countAfter = filmStorage.findAll().size();

        Assertions.assertEquals(countAfter, countBefore + 1);
        Assertions.assertNotNull(addedFilm.getId());
    }

    @Test
    public void updateFilm_mustReturnUpdatedFilm() {
        Film film = filmStorage.findById(1L).get();
        film.setDuration(90);
        film.setDescription("Film1DescriptionUpdated");

        filmStorage.update(film);
        Film updatedFilm = filmStorage.findById(1L).get();

        Assertions.assertEquals(film.getDescription(), updatedFilm.getDescription());
        Assertions.assertEquals(film.getDuration(), updatedFilm.getDuration());
        Assertions.assertEquals(film.getId(), updatedFilm.getId());
    }

    @Test
    public void deleteFilmById_mustCorrectDelete() {
        int countBefore = filmStorage.findAll().size();
        filmStorage.deleteById(1L);
        int countAfter = filmStorage.findAll().size();

        Optional<Film> filmOptional = filmStorage.findById(1L);

        assertThat(filmOptional).isEmpty();
        Assertions.assertEquals(countAfter, countBefore - 1);
    }

    @Test
    public void addFilmLike_mustIncreaseLikeCount() {
        Film film = filmStorage.findById(2L).get();
        int countBefore = film.getLikes().size();
        filmStorage.addLike(film.getId(), 1L);
        film = filmStorage.findById(2L).get();
        int countAfter = film.getLikes().size();

        Assertions.assertEquals(countAfter, countBefore + 1);
    }

    @Test
    public void deleteFilmLike_mustDecreaseLikeCount() {
        Film film = filmStorage.findById(2L).get();
        int countBefore = film.getLikes().size();
        filmStorage.deleteLike(film.getId(), 2L);
        film = filmStorage.findById(2L).get();
        int countAfter = film.getLikes().size();

        Assertions.assertEquals(countAfter, countBefore - 1);
    }

    @Test
    public void getPopular_mustReturnPopularFilm() {
        Film film = filmStorage.getPopular(1L)
                .stream()
                .findFirst()
                .orElse(null);

        assert film != null;
        Assertions.assertEquals(3L, film.getId());
    }
}
