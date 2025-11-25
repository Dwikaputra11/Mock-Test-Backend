package com.kiwadev.mocktest.services;

import com.kiwadev.mocktest.models.domain.Movie;
import com.kiwadev.mocktest.models.domain.User;
import com.kiwadev.mocktest.models.web.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieService {
    List<MovieResponseDTO> findAll(Pageable pageable);
    MovieResponseDTO findById(Long id);
    MovieResponseDTO save(MovieRequestDTO movie);
    MovieResponseDTO update(Long id, MovieRequestDTO movie);
    void delete(Long id);
}
