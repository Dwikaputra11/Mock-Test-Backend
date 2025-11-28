package com.kiwadev.mocktest.models.web;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryResponseDTO {
    private  String countryCode;
    private String name;
}
