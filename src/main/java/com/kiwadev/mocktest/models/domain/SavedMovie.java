package com.kiwadev.mocktest.models.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "saved_movie", schema = "mock_test")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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

    private LocalDate dateCreated;
}
