package com.kiwadev.mocktest.models.web;

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
    private Long genreId;
}
