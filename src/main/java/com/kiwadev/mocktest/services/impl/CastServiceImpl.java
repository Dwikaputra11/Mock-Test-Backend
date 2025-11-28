package com.kiwadev.mocktest.services.impl;

import com.kiwadev.mocktest.models.domain.Actor;
import com.kiwadev.mocktest.models.domain.Cast;
import com.kiwadev.mocktest.models.domain.CastId;
import com.kiwadev.mocktest.models.domain.Movie;
import com.kiwadev.mocktest.models.web.CastRequestDTO;
import com.kiwadev.mocktest.models.web.CastResponseDTO;
import com.kiwadev.mocktest.repository.ActorRepository;
import com.kiwadev.mocktest.repository.CastRepository;
import com.kiwadev.mocktest.repository.MovieRepository;
import com.kiwadev.mocktest.services.CastService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CastServiceImpl implements CastService {

    private final CastRepository  castRepository;
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;

    @Override
    public List<CastResponseDTO> addCast(Long movieId, List<CastRequestDTO> actors) {

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        List<Cast> castList = new ArrayList<>();

        for (CastRequestDTO dto : actors) {

            Actor actor = actorRepository.findById(dto.getActorId())
                    .orElseThrow(() -> new RuntimeException("Actor not found"));

            CastId id = new CastId(movieId, dto.getActorId());

            // prevent duplicates
            if (castRepository.existsById(id)) {
                continue;
            }

            Cast cast = new Cast();
            cast.setId(id);
            cast.setMovie(movie);
            cast.setActor(actor);

            castList.add(cast);
        }

        List<Cast> saved = castRepository.saveAll(castList);

        return saved.stream()
                .map(c -> new CastResponseDTO(
                        c.getMovie().getMovieId(),
                        c.getActor().getActorId(),
                        c.getActor().getName()
                ))
                .toList();
    }

    @Override
    public void deleteCast(Long movieId, Long actorId) {
        CastId id = new CastId(movieId, actorId);

        if (!castRepository.existsById(id)) {
            throw new RuntimeException("Cast not found");
        }

        castRepository.deleteById(id);
    }

    @Override
    public List<CastResponseDTO> getCastByMovie(Long movieId) {

        List<Cast> casts = castRepository.findByMovie_MovieId(movieId);

        return casts.stream()
                .map(c -> new CastResponseDTO(
                        c.getMovie().getMovieId(),
                        c.getActor().getActorId(),
                        c.getActor().getName()
                ))
                .toList();
    }
}

