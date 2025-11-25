package com.kiwadev.mocktest.models.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "cast", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
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
}