package com.kiwadev.mocktest.repository;

import com.kiwadev.mocktest.models.domain.Cast;
import com.kiwadev.mocktest.models.domain.CastId;
import com.kiwadev.mocktest.models.domain.MovieAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CastRepository extends JpaRepository<Cast, CastId> {
    List<Cast> findByMovie_MovieId(Long movieId);
}
