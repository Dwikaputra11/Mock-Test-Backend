package com.kiwadev.mocktest.services;

import com.kiwadev.mocktest.models.web.ActorRequestDTO;
import com.kiwadev.mocktest.models.web.ActorResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GenreService {
    List<ActorResponseDTO> findAll(Pageable pageable);
    ActorResponseDTO findById(Long id);
    ActorResponseDTO save(ActorRequestDTO movie);
    ActorResponseDTO update(Long id, ActorRequestDTO movie);
    void delete(Long id);
}
