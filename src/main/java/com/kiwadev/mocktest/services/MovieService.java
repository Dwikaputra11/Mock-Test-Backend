package com.kiwadev.mocktest.services;

import com.kiwadev.mocktest.models.domain.Movie;
import com.kiwadev.mocktest.models.domain.User;
import com.kiwadev.mocktest.models.web.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieService {

    MovieResponseDTO save(MovieRequestDTO movie);

    MovieResponseDTO update(Long movieId, MovieRequestDTO dto);

    void delete(Long movieId);

    MovieResponseDTO findById(Long movieId);

    Page<MovieResponseDTO> findAll(Pageable pageable);

    // include related tables
    List<CastResponseDTO> getMovieCast(Long movieId);
    List<MovieAvailabilityResponseDTO> getAvailability(Long movieId);
    List<MovieResponseDTO> getSavedMovie(Long userId);
    List<MovieAvailabilityResponseDTO> getAvailability(String movieCode);
}
