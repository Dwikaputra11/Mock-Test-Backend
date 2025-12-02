package com.kiwadev.mocktest.services;

import com.kiwadev.mocktest.models.web.ActorRequestDTO;
import com.kiwadev.mocktest.models.web.ActorResponseDTO;
import com.kiwadev.mocktest.models.web.GenreRequestDTO;
import com.kiwadev.mocktest.models.web.GenreResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GenreService {
    Page<GenreResponseDTO> findAll(Pageable pageable);
    GenreResponseDTO findById(Long id);
    GenreResponseDTO create(GenreRequestDTO genre);
    GenreResponseDTO update(Long id, GenreRequestDTO genre);
    void delete(Long id);
}
