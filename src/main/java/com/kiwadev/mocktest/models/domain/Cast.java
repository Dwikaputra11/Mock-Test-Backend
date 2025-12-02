package com.kiwadev.mocktest.models.domain;

import com.kiwadev.mocktest.models.web.CastResponseDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cast", schema = "mock_test")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cast {

    @EmbeddedId
    private CastId id;

    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @MapsId("actorId")
    @JoinColumn(name = "actor_id")
    private Actor actor;

    public CastResponseDTO toResponse()  {
        return CastResponseDTO.builder()
                .movieId(movie.getMovieId())
                .actorId(actor.getActorId())
                .actorName(actor.getName())
                .build();
    }
}