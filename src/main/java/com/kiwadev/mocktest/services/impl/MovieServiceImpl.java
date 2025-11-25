package com.kiwadev.mocktest.services.impl;

import com.kiwadev.mocktest.models.domain.Movie;
import com.kiwadev.mocktest.models.web.MovieRequestDTO;
import com.kiwadev.mocktest.models.web.MovieResponseDTO;
import com.kiwadev.mocktest.repository.MovieRepository;
import com.kiwadev.mocktest.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository repository;

    @Override
    public List<MovieResponseDTO> findAll(Pageable pageable) {
        var list =  repository.findAll(pageable);

        return  list.stream().map(Movie::toMovieResponse).toList();
    }

    @Override
    public MovieResponseDTO findById(Long id) {
        return null;
    }

    @Override
    public MovieResponseDTO save(MovieRequestDTO movie) {
        return null;
    }

    @Override
    public MovieResponseDTO update(Long id, MovieRequestDTO movie) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
