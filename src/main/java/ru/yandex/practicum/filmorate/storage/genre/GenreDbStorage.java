package ru.yandex.practicum.filmorate.storage.genre;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.genre.Genre;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre add(Genre item) {
        String insertQuery = "INSERT INTO genres (name) VALUES (?)";
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(insertQuery, Types.VARCHAR);
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Collections.singletonList(item.getName()));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        pscf.setReturnGeneratedKeys(true);
        jdbcTemplate.update(psc, keyHolder);

        Long id = (Long) keyHolder.getKey();
        return findById(id).orElse(null);
    }

    @Override
    public Genre update(Genre item) {
        String updateQuery = "UPDATE genres SET name=? WHERE genre_id=?";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(updateQuery);
            ps.setString(1, item.getName());
            ps.setLong(2, item.getId());
            return ps;
        });
        return findById(item.getId()).orElse(null);
    }

    @Override
    public Optional<Genre> findById(Long id) {
        String findByIdQuery = "SELECT genre_id, name FROM genres WHERE genre_id=?";
        return jdbcTemplate.query(findByIdQuery, new GenreMapper(), id)
                .stream()
                .findAny();
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Genre> findAll() {
        String findAllQuery = "SELECT genre_id, name FROM genres";
        return jdbcTemplate.query(findAllQuery, new GenreMapper());
    }

    @Override
    public void deleteById(Long id) {
        String deleteByIdQuery = "DELETE FROM genres WHERE genre_id=?";
        jdbcTemplate.update(deleteByIdQuery, id);
    }
}
