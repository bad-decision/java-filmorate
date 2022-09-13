package ru.yandex.practicum.filmorate.storage.mpa;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.mpa.Mpa;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Mpa add(Mpa item) {
        String insertQuery = "INSERT INTO mpa (name) VALUES (?)";
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(insertQuery, Types.VARCHAR);
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Collections.singletonList(item.getName()));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        pscf.setReturnGeneratedKeys(true);
        jdbcTemplate.update(psc, keyHolder);

        Long id = (Long) keyHolder.getKey();
        return findById(id).orElse(null);
    }

    @Override
    public Mpa update(Mpa item) {
        String updateQuery = "UPDATE mpa SET name=? WHERE mpa_id=?";
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
    public Optional<Mpa> findById(Long id) {
        String findByIdQuery = "SELECT mpa_id, name FROM mpa WHERE mpa_id=?";
        return jdbcTemplate.query(findByIdQuery, new MpaMapper(), id)
                .stream()
                .findAny();
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Mpa> findAll() {
        String findAllQuery = "SELECT mpa_id, name FROM mpa";
        return jdbcTemplate.query(findAllQuery, new MpaMapper());
    }

    @Override
    public void deleteById(Long id) {
        String deleteByIdQuery = "DELETE FROM mpa WHERE mpa_id=?";
        jdbcTemplate.update(deleteByIdQuery, id);
    }
}
