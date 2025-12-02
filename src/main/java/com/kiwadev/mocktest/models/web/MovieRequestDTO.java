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
public class MovieRequestDTO {
    private String title;
    private Integer year;
    @JsonProperty("genre_id")
    private Long genreId;
}
