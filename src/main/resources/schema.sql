DROP TABLE IF EXISTS film_genres;
DROP TABLE IF EXISTS likes;
DROP TABLE IF EXISTS friendship;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS films;
DROP TABLE IF EXISTS mpa;
DROP TABLE IF EXISTS genres;

CREATE TABLE genres (
    genre_id bigint PRIMARY KEY AUTO_INCREMENT,
    name varchar
);

CREATE TABLE mpa (
    mpa_id bigint PRIMARY KEY AUTO_INCREMENT,
    name varchar
);

CREATE TABLE films (
    film_id bigint PRIMARY KEY AUTO_INCREMENT,
    name varchar,
    description varchar,
    release_date date,
    duration int,
    rate int,
    mpa_id bigint REFERENCES mpa(mpa_id)
);

CREATE TABLE users (
    user_id bigint PRIMARY KEY AUTO_INCREMENT,
    email varchar,
    login varchar,
    birthday date,
    name varchar
);

CREATE TABLE film_genres (
    film_id bigint REFERENCES films(film_id),
    genre_id bigint REFERENCES genres(genre_id),
    primary key (film_id, genre_id)
);

CREATE TABLE likes (
    film_id bigint REFERENCES films(film_id),
    user_id bigint REFERENCES users(user_id),
    primary key (film_id, user_id)
);

CREATE TABLE friendship (
    from_user_id bigint REFERENCES users(user_id),
    to_user_id bigint REFERENCES users(user_id),
    is_confirmed bool,
    primary key (from_user_id, to_user_id)
);