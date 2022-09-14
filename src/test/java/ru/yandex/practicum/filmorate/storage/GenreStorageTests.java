package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.genre.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreStorageTests {
    private final GenreStorage genreStorage;

    @Test
    public void findGenreById_mustReturnCorrectGenre() {

        Optional<Genre> genreOptional = genreStorage.findById(1L);

        assertThat(genreOptional)
                .isPresent()
                .hasValueSatisfying(genre ->
                        assertThat(genre)
                                .hasFieldOrPropertyWithValue("id", 1L)
                                .hasFieldOrPropertyWithValue("name", "Комедия")
                );
    }

    @Test
    public void findAllGenres_mustReturnCorrectGenre() {
        List<Genre> genres = genreStorage.findAll();
        assertThat(genres).isNotEmpty();
    }

    @Test
    public void existGenreById_mustReturnTrue() {
        Assertions.assertTrue(genreStorage.existsById(1L));
    }
}
