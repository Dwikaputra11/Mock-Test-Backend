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
public class DeleteSavedMovieRequestDTO {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("movie_id")
    private Long movieId;
}