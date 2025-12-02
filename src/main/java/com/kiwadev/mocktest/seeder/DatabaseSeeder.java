package com.kiwadev.mocktest.seeder;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiwadev.mocktest.models.domain.*;
import com.kiwadev.mocktest.models.web.MovieRequestDTO;
import com.kiwadev.mocktest.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
//@Component
@RequiredArgsConstructor
public class DatabaseSeeder {

    private final MovieRepository             movieRepository;
    private final UserRepository              userRepository;
    private final ActorRepository             actorRepository;
    private final GenreRepository             genreRepository;
    private final CountryRepository           countryRepository;


    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

//    @EventListener
    public void seed(ContextRefreshedEvent event) throws IOException {
        seedMovieTable();
        seedGenreTable();
        seedActorTable();
        seedCountryTable();
    }

    public void seedGenreTable() throws IOException {
        var sql = "SELECT COUNT(*) FROM public.genre";
        var rs  = jdbcTemplate.queryForObject(sql, Integer.class);

        if (rs != null && rs == 0) {
            ClassPathResource res = new ClassPathResource("seeds/genre.json");

            List<Genre> genres = objectMapper.readValue(
                    res.getInputStream(),
                    new TypeReference<>() {
                    }
            );

            log.info("genre data: {}", genres);
            var saved = genreRepository.saveAll(genres);
            log.info("genre data seeded: {}", saved);
        }
    }

    public void seedActorTable() throws IOException {
        var sql = "SELECT COUNT(*) FROM public.actor";
        var rs  = jdbcTemplate.queryForObject(sql, Integer.class);

        if (rs != null && rs == 0) {
            ClassPathResource res = new ClassPathResource("seeds/actor.json");

            List<Actor> actors = objectMapper.readValue(
                    res.getInputStream(),
                    new TypeReference<>() {
                    }
            );

            log.info("actor data: {}", actors);
            var saved = actorRepository.saveAll(actors);
            log.info("actor data seeded: {}", saved);
        }
    }

    public void seedCountryTable() throws IOException {
        var sql = "SELECT COUNT(*) FROM public.country";
        var rs  = jdbcTemplate.queryForObject(sql, Integer.class);

        if (rs != null && rs == 0) {
            ClassPathResource res = new ClassPathResource("seeds/country.json");

            List<Country> countries = objectMapper.readValue(
                    res.getInputStream(),
                    new TypeReference<>() {
                    }
            );

            log.info("country data: {}", countries);
            var saved = countryRepository.saveAll(countries);
            log.info("country data seeded: {}", saved);
        }
    }

    public void seedMovieTable() throws IOException {
        var sql = "SELECT COUNT(*) FROM public.movie";
        var rs  = jdbcTemplate.queryForObject(sql, Integer.class);

        if (rs != null && rs == 0) {
            ClassPathResource res = new ClassPathResource("seeds/movie.json");

            List<MovieRequestDTO> dtos = objectMapper.readValue(
                    res.getInputStream(),
                    new TypeReference<>() {
                    }
            );

            List<Movie> movies = dtos.stream().map(dto ->
                    Movie.builder()
                            .title(dto.getTitle())
                            .year(dto.getYear())
                            .genre(genreRepository.getReferenceById(dto.getGenreId()))
                            .build()
            ).toList();

            log.info("movie data: {}", movies);
            var saved = movieRepository.saveAll(movies);
            log.info("movie data seeded: {}", saved);
        }
    }
}
