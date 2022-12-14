DROP TABLE IF EXISTS film_genres;
DROP TABLE IF EXISTS likes;
DROP TABLE IF EXISTS friendship;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS films;
DROP TABLE IF EXISTS mpa;
DROP TABLE IF EXISTS genres;

CREATE TABLE genres (
    genre_id bigint PRIMARY KEY AUTO_INCREMENT,
    name varchar(64)
);

CREATE TABLE mpa (
    mpa_id bigint PRIMARY KEY AUTO_INCREMENT,
    name varchar(64)
);

CREATE TABLE films (
    film_id bigint PRIMARY KEY AUTO_INCREMENT,
    name varchar(128),
    description varchar(512),
    release_date date,
    duration int,
    rate int,
    mpa_id bigint REFERENCES mpa(mpa_id) ON DELETE CASCADE
);

CREATE TABLE users (
    user_id bigint PRIMARY KEY AUTO_INCREMENT,
    email varchar(64),
    login varchar(64),
    birthday date,
    name varchar(64)
);

CREATE TABLE film_genres (
    film_id bigint REFERENCES films(film_id) ON DELETE CASCADE,
    genre_id bigint REFERENCES genres(genre_id) ON DELETE CASCADE,
    primary key (film_id, genre_id)
);

CREATE TABLE likes (
    film_id bigint REFERENCES films(film_id) ON DELETE CASCADE,
    user_id bigint REFERENCES users(user_id) ON DELETE CASCADE,
    primary key (film_id, user_id)
);

CREATE TABLE friendship (
    from_user_id bigint REFERENCES users(user_id) ON DELETE CASCADE,
    to_user_id bigint REFERENCES users(user_id) ON DELETE CASCADE,
    is_confirmed bool,
    primary key (from_user_id, to_user_id)
);