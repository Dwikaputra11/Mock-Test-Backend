package com.kiwadev.mocktest.models.web;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieAvailabilityResponseDTO {
    private Long movieId;
    private String movieName;
    private String countryCode;
    private String countryName;
}
