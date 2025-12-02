package com.kiwadev.mocktest.repository;

import com.kiwadev.mocktest.models.domain.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("""
            SELECT m FROM Movie m
            join MovieAvailability ma on ma.movie.id = m.id
            LEFT JOIN Genre g on m.genre.id = g.id
            WHERE (:country is NULL or ma.country.countryCode = :country)
            AND (:genre IS NULL OR g.genreId = :genre)
            GROUP BY m.id
           """)
    Page<Movie> findAllFiltered(
            @Param("country") String country,
            @Param("genre") Long genre,
            Pageable pageable
    );


}
