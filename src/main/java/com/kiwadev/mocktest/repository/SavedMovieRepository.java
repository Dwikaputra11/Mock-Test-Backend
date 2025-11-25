package com.kiwadev.mocktest.repository;

import com.kiwadev.mocktest.models.domain.SavedMovie;
import com.kiwadev.mocktest.models.domain.SavedMovieId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedMovieRepository extends JpaRepository<SavedMovie, SavedMovieId> {
}
