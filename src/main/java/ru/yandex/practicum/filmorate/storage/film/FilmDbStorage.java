package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.genre.Genre;
import ru.yandex.practicum.filmorate.model.like.Like;
import ru.yandex.practicum.filmorate.storage.genre.GenreMapper;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final String insertFilmGenreQuery = "INSERT INTO film_genres (film_id, genre_id) VALUES (?,?)";


    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film add(Film item) {
        String insertFilmQuery = "INSERT INTO films (name, description, release_date, duration, rate, mpa_id) VALUES (?,?,?,?,?,?)";
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(insertFilmQuery,
                Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.INTEGER, Types.INTEGER, Types.INTEGER);
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(item.getName(), item.getDescription(),
                Date.valueOf(item.getReleaseDate()), item.getDuration(), item.getRate(), item.getMpa().getId()));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        pscf.setReturnGeneratedKeys(true);
        jdbcTemplate.update(psc, keyHolder);

        Long filmId = (Long) keyHolder.getKey();

        for (Genre genre : item.getGenres()) {
            jdbcTemplate.update(insertFilmGenreQuery, filmId, genre.getId());
        }

        return findById(filmId).orElse(null);
    }

    @Override
    public Film update(Film item) {
        Film existFilm = findById(item.getId()).get();

        String updateQuery = "UPDATE films SET name=?, description=?, release_date=?, duration=?, rate=?, mpa_id=? WHERE film_id=?";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(updateQuery);
            ps.setString(1, item.getName());
            ps.setString(2, item.getDescription());
            ps.setDate(3, Date.valueOf(item.getReleaseDate()));
            ps.setInt(4, item.getDuration());
            if (item.getRate() == null)
                ps.setNull(5, Types.NULL);
            else
                ps.setInt(5, item.getRate());
            ps.setLong(6, item.getMpa().getId());
            ps.setLong(7, item.getId());
            return ps;
        });

        String deleteFilmGenreQuery = "DELETE FROM film_genres WHERE film_id=? AND genre_id=?";
        for (Genre existFilmGenre : existFilm.getGenres()) {
            if (item.getGenres().stream().noneMatch(x -> x.getId().equals(existFilmGenre.getId()))) {
                jdbcTemplate.update(deleteFilmGenreQuery, item.getId(), existFilmGenre.getId());
            }
        }

        for (Genre filmGenre : item.getGenres()) {
            if (existFilm.getGenres().stream().noneMatch(x -> x.getId().equals(filmGenre.getId()))) {
                jdbcTemplate.update(insertFilmGenreQuery, item.getId(), filmGenre.getId());
            }
        }

        return findById(item.getId()).orElse(null);
    }

    @Override
    public Optional<Film> findById(Long id) {
        String findByIdQuery = "SELECT film_id, f.name as name, description, release_date, duration, rate, m.mpa_id " +
                "as mpa_id, m.name as mpa_name FROM films f JOIN mpa m ON f.mpa_id=m.mpa_id WHERE film_id=?";
        Optional<Film> filmOpt = jdbcTemplate.query(findByIdQuery, new FilmMapper(), id)
                .stream()
                .findAny();

        if (filmOpt.isPresent()) {
            String findGenresByFilmIdQuery = "SELECT fg.genre_id as genre_id, name FROM film_genres fg JOIN genres g " +
                    "ON fg.genre_id=g.genre_id WHERE film_id=?";
            List<Genre> genres = jdbcTemplate.query(findGenresByFilmIdQuery, new GenreMapper(), id);
            filmOpt.get().getGenres().addAll(genres);

            String findLikesByFilmIdQuery = "SELECT user_id, film_id FROM likes WHERE film_id=?";
            List<Like> likes = jdbcTemplate.query(findLikesByFilmIdQuery, new LikeMapper(), id);
            filmOpt.get().getLikes().addAll(likes.stream().map(Like::getUserId).collect(Collectors.toList()));
        }

        return filmOpt;
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Film> findAll() {
        String findAllQuery = "SELECT film_id, f.name as name, description, release_date, duration, rate, " +
                "m.mpa_id as mpa_id, m.name as mpa_name FROM films f JOIN mpa m ON f.mpa_id=m.mpa_id";
        return jdbcTemplate.query(findAllQuery, new FilmMapper());
    }

    @Override
    public void deleteById(Long id) {
        String deleteByIdQuery = "DELETE FROM films WHERE film_id=?";
        jdbcTemplate.update(deleteByIdQuery, id);
    }

    @Override
    public Film addLike(Long filmId, Long userId) {
        String insertLikeQuery = "INSERT INTO likes (film_id, user_id) VALUES (?,?)";
        jdbcTemplate.update(insertLikeQuery, filmId, userId);
        return findById(filmId).orElse(null);
    }

    @Override
    public Film deleteLike(Long filmId, Long userId) {
        String deleteLikeQuery = "DELETE FROM likes WHERE film_id=? AND user_id=?";
        jdbcTemplate.update(deleteLikeQuery, filmId, userId);
        return findById(filmId).orElse(null);
    }

    @Override
    public List<Film> getPopular(Long count) {
        String findPopularQuery = "SELECT film_id, f.name as name, description, release_date, duration, rate, " +
                "m.mpa_id as mpa_id, m.name as mpa_name FROM films f JOIN mpa m ON f.mpa_id=m.mpa_id WHERE film_id IN " +
                "(SELECT f.film_id FROM films f LEFT JOIN likes l ON f.film_id=l.film_id GROUP BY f.film_id ORDER BY COUNT(l.user_id) DESC LIMIT ?) ";
        return jdbcTemplate.query(findPopularQuery, new FilmMapper(), count); // WHERE l.film_id IS NOT NULL
    }
}
