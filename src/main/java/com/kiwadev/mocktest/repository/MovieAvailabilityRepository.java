package com.kiwadev.mocktest.repository;

import com.kiwadev.mocktest.models.domain.MovieAvailability;
import com.kiwadev.mocktest.models.domain.MovieAvailabilityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieAvailabilityRepository extends JpaRepository<MovieAvailability, MovieAvailabilityId> {
    List<MovieAvailability> findByMovie_MovieId(Long movieId);
    List<MovieAvailability> findByCountry_CountryCode(String countryCode);
}
