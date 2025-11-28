package com.kiwadev.mocktest.services;

import com.kiwadev.mocktest.models.web.CastRequestDTO;
import com.kiwadev.mocktest.models.web.CastResponseDTO;

import java.util.List;

public interface CastService {
    List<CastResponseDTO> addCast(Long movieId, List<CastRequestDTO> actors);

    void deleteCast(Long movieId, Long actorId);

    List<CastResponseDTO> getCastByMovie(Long movieId);
}
