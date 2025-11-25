package com.kiwadev.mocktest.models.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "saved_movie", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
public class SavedMovie {

    @EmbeddedId
    private SavedMovieId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private Long dateCreated;
}
