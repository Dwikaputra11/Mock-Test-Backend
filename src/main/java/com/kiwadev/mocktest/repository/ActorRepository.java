package com.kiwadev.mocktest.repository;

import com.kiwadev.mocktest.models.domain.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Long> {
}
