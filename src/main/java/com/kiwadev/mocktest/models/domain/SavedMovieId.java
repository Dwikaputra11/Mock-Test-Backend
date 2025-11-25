package com.kiwadev.mocktest.models.domain;


import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;


@Setter
@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SavedMovieId implements Serializable {
    private Long userId;
    private Long movieId;
}
