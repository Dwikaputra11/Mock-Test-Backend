package com.kiwadev.mocktest.repository;

import com.kiwadev.mocktest.models.domain.Cast;
import com.kiwadev.mocktest.models.domain.CastId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CastRepository extends JpaRepository<Cast, CastId> {
}
