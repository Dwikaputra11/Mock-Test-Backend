package com.kiwadev.mocktest.services.impl;

import com.kiwadev.mocktest.models.domain.Cast;
import com.kiwadev.mocktest.models.domain.Genre;
import com.kiwadev.mocktest.models.domain.Movie;
import com.kiwadev.mocktest.models.domain.MovieAvailability;
import com.kiwadev.mocktest.models.web.CastResponseDTO;
import com.kiwadev.mocktest.models.web.MovieAvailabilityResponseDTO;
import com.kiwadev.mocktest.models.web.MovieRequestDTO;
import com.kiwadev.mocktest.models.web.MovieResponseDTO;
import com.kiwadev.mocktest.repository.*;
import com.kiwadev.mocktest.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final CastRepository  castRepository;
    private final MovieAvailabilityRepository availabilityRepository;
    private final SavedMovieRepository        savedMovieRepository;
    @Override
    public MovieResponseDTO save(MovieRequestDTO dto) {
        Genre genre = genreRepository.findById(dto.getGenreId())
                .orElseThrow(() -> new RuntimeException("Genre not found"));

        Movie movie = Movie.builder()
                .genre(genre)
                .title(dto.getTitle())
                .year(dto.getYear())
                .build();

        movieRepository.save(movie);

        return movie.toMovieResponse();
    }

    @Override
    public MovieResponseDTO update(Long movieId, MovieRequestDTO dto) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Genre genre = genreRepository.findById(dto.getGenreId())
                .orElseThrow(() -> new RuntimeException("Genre not found"));

        movie.setGenre(genre);
        movie.setTitle(dto.getTitle());
        movie.setYear(dto.getYear());

        movieRepository.save(movie);

        return movie.toMovieResponse();
    }

    @Override
    public void delete(Long movieId) {
        movieRepository.deleteById(movieId);
    }

    @Override
    public MovieResponseDTO findById(Long movieId) {
        return movieRepository.findById(movieId)
                .map(Movie::toMovieResponse)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
    }

    @Override
    public Page<MovieResponseDTO> findAll(Pageable pageable) {
        return movieRepository.findAll(pageable).map(Movie::toMovieResponse);
    }

    @Override
    public List<CastResponseDTO> getMovieCast(Long movieId) {
        return castRepository.findByMovie_MovieId(movieId)
                .stream()
                .map(Cast::toResponse).toList();
    }

    @Override
    public List<MovieAvailabilityResponseDTO> getAvailability(Long movieId) {
        return availabilityRepository.findByMovie_MovieId(movieId)
                .stream()
                .map(MovieAvailability::toResponse).toList();
    }

    @Override
    public List<MovieResponseDTO> getSavedMovie(Long userId) {
        return savedMovieRepository.findByUser_UserId(userId)
                .stream()
                .map(saved -> saved.getMovie().toMovieResponse())
                .toList();
    }

    @Override
    public List<MovieAvailabilityResponseDTO> getAvailability(String countryCode) {
        return availabilityRepository.findByCountry_CountryCode(countryCode)
                .stream()
                .map(MovieAvailability::toResponse).toList();
    }

}
