package com.kiwadev.mocktest.models.domain;

import com.kiwadev.mocktest.models.web.MovieAvailabilityResponseDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "movie_availability", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovieAvailability {

    @EmbeddedId
    private MovieAvailabilityId id;

    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @MapsId("countryCode")
    @JoinColumn(name = "country_code")
    private Country country;


    public MovieAvailabilityResponseDTO toResponse(){
        return MovieAvailabilityResponseDTO.builder()
                .movieId(movie.getMovieId())
                .movieName(movie.getTitle())
                .countryCode(country.getCountryCode())
                .countryName(country.getName())
                .build();
    }
}
