package com.kiwadev.mocktest.models.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CastId implements Serializable {
    private Long movieId;
    private Long actorId;
}