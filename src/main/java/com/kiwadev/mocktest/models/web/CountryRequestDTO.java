package com.kiwadev.mocktest.models.web;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryRequestDTO {
    private String countryCode;
    private String name;
}
