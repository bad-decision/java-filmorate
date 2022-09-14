INSERT INTO genres (NAME)
VALUES ( 'Комедия' ),
       ('Драма'),
       ('Мультфильм'),
       ('Триллер'),
       ('Документальный'),
       ('Боевик');

INSERT INTO mpa (NAME)
VALUES ( 'G' ),
       ('PG'),
       ('PG-13'),
       ('R'),
       ('NC-17');

INSERT INTO users (name, login, email, birthday)
VALUES ('User1Name', 'User1Login', 'user1@user.com', '2000-01-05'),
       ('User2Name', 'User2Login', 'user2@user.com', '2000-02-06'),
       ('User3Name', 'User3Login', 'user3@user.com', '2001-02-08');

INSERT INTO films (name, description, release_date, duration, rate, mpa_id)
VALUES ('Film1Name', 'Film1Description', '2000-01-05', 100, 1, 1),
       ('Film2Name', 'Film2Description', '2000-02-06', 150, 2, 2),
       ('Film3Name', 'Film3Description', '2000-02-04', 130, 3, 1);

INSERT INTO film_genres (film_id, genre_id)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (2, 3);

INSERT INTO likes (film_id, user_id)
VALUES (3, 1),
       (3, 2),
       (2, 2);

INSERT INTO friendship (from_user_id, to_user_id, is_confirmed)
VALUES (1, 2, 0);