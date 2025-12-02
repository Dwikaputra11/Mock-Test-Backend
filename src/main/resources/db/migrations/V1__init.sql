CREATE SCHEMA IF NOT EXISTS mock_test;
SET search_path TO mock_test;

CREATE TABLE actor (
    actor_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE country (
    country_code VARCHAR(10) PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE genre (
    genre_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

ALTER SEQUENCE genre_genre_id_seq RESTART WITH 1;
CREATE UNIQUE INDEX uq_genres_name_ci ON genre (LOWER(name));


CREATE TABLE movie (
    movie_id SERIAL PRIMARY KEY,
    genre_id BIGINT,
    title VARCHAR(255) NOT NULL,
    year INT,

    CONSTRAINT fk_movie_genre FOREIGN KEY (genre_id)
        REFERENCES genre(genre_id)
);

CREATE INDEX idx_movie_year
    ON movie(year);

CREATE TABLE cast (
    movie_id BIGINT NOT NULL,
    actor_id BIGINT NOT NULL,

    PRIMARY KEY (movie_id, actor_id),

    CONSTRAINT fk_cast_movie FOREIGN KEY (movie_id)
        REFERENCES movie(movie_id),

    CONSTRAINT fk_cast_actor FOREIGN KEY (actor_id)
        REFERENCES actor(actor_id)
);

CREATE TABLE movie_availability (
    movie_id BIGINT NOT NULL,
    country_code VARCHAR(10) NOT NULL,

    PRIMARY KEY (movie_id, country_code),

    CONSTRAINT fk_avail_movie FOREIGN KEY (movie_id)
        REFERENCES movie(movie_id),

    CONSTRAINT fk_avail_country FOREIGN KEY (country_code)
        REFERENCES country(country_code)
);

CREATE INDEX idx_availability_country_movie
    ON movie_availability(country_code, movie_id);

CREATE TABLE "user" (
    user_id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    date_birth BIGINT
);

CREATE TABLE saved_movie (
    user_id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,
    date_created TIMESTAMP WITH TIME ZONE DEFAULT NOW(),

    PRIMARY KEY (user_id, movie_id),

    CONSTRAINT fk_saved_user FOREIGN KEY (user_id)
        REFERENCES "user"(user_id),

    CONSTRAINT fk_saved_movie FOREIGN KEY (movie_id)
        REFERENCES movie(movie_id)
);

CREATE INDEX idx_saved_user_date
    ON saved_movie(user_id, date_created);