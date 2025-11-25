package com.kiwadev.mocktest.models.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kiwadev.mocktest.models.web.AuthResponseDTO;
import com.kiwadev.mocktest.models.web.MovieResponseDTO;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "movie", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
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
                .title(title)
                .year(year)
                .genreName(genre.getName())
                .build();
    }
}
