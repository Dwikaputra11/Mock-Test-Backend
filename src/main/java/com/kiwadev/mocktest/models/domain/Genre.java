package com.kiwadev.mocktest.models.domain;

import com.kiwadev.mocktest.models.web.GenreResponseDTO;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "genre", schema = "mock_test")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Genre implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private Long genreId;
    private String name;

    @OneToMany(mappedBy = "genre")
    private List<Movie> movies;

    public GenreResponseDTO toResponse() {
        return GenreResponseDTO.builder()
                .id(genreId)
                .name(name)
                .build();
    }
}
