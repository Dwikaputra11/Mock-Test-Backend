package com.kiwadev.mocktest.models.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "saved_movie", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
