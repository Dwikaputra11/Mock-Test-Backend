package com.kiwadev.mocktest.models.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponseDTO {
    @JsonProperty("movie_id")
    private Long movieId;
    private Integer year;
    private String title;
    private String genreName;
}
