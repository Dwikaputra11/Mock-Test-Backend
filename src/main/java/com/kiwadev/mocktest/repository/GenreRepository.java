package com.kiwadev.mocktest.repository;

import com.kiwadev.mocktest.models.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
