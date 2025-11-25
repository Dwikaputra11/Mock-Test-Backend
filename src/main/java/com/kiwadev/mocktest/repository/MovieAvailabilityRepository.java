package com.kiwadev.mocktest.repository;

import com.kiwadev.mocktest.models.domain.MovieAvailability;
import com.kiwadev.mocktest.models.domain.MovieAvailabilityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieAvailabilityRepository extends JpaRepository<MovieAvailability, MovieAvailabilityId> {
}
