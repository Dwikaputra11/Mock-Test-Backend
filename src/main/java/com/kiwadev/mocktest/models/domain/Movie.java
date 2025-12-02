package com.kiwadev.mocktest.models.domain;

import com.kiwadev.mocktest.models.web.MovieResponseDTO;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "movie", schema = "mock_test")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Movie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long movieId;
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;
    private String title;
    private Integer year;

    @OneToMany(mappedBy = "movie")
    private Set<SavedMovie> savedMovies;


    public MovieResponseDTO toMovieResponse(){
        return MovieResponseDTO.builder()
                .movieId(movieId)
                .title(title)
                .year(year)
                .genreName(genre.getName())
                .build();
    }
}
