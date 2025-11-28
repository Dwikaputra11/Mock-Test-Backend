package com.kiwadev.mocktest.repository;

import com.kiwadev.mocktest.models.domain.MovieAvailability;
import com.kiwadev.mocktest.models.domain.SavedMovie;
import com.kiwadev.mocktest.models.domain.SavedMovieId;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavedMovieRepository extends JpaRepository<SavedMovie, SavedMovieId> {
    @EntityGraph(attributePaths = {"movie", "movie.genre"})
    List<SavedMovie> findByMovie_MovieId(Long movieId);
    @EntityGraph(attributePaths = {"movie", "movie.genre"})
    List<SavedMovie> findByUser_UserId(Long userId);
}
