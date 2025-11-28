package com.kiwadev.mocktest.models.web;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CastResponseDTO {
    private Long movieId;
    private Long actorId;
    private String actorName;
}
