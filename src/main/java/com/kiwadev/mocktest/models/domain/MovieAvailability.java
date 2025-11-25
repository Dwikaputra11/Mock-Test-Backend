package com.kiwadev.mocktest.models.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "movie_availability", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
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
}
