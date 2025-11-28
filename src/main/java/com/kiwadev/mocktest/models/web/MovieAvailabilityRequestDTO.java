package com.kiwadev.mocktest.models.web;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieAvailabilityRequestDTO {
    private String countryCode;
}
