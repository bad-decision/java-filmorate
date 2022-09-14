package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.mpa.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MpaStorageTests {
    private final MpaStorage mpaStorage;

    @Test
    public void findMpaById_mustReturnCorrectMpa() {

        Optional<Mpa> mpaOptional = mpaStorage.findById(1L);

        assertThat(mpaOptional)
                .isPresent()
                .hasValueSatisfying(mpa ->
                        assertThat(mpa)
                                .hasFieldOrPropertyWithValue("id", 1L)
                                .hasFieldOrPropertyWithValue("name", "G")
                );
    }

    @Test
    public void findAllMpa_mustReturnCorrectMpa() {
        List<Mpa> mpa = mpaStorage.findAll();
        assertThat(mpa).isNotEmpty();
    }

    @Test
    public void existMpaById_mustReturnTrue() {
        Assertions.assertTrue(mpaStorage.existsById(1L));
    }
}
