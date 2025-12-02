package com.kiwadev.mocktest.models.domain;


import com.kiwadev.mocktest.models.web.CountryResponseDTO;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "country", schema = "mock_test")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Country implements Serializable {
    @Id
    @Column(name = "country_code")
    private String countryCode;
    private String name;

    public CountryResponseDTO toResponse(){
        return CountryResponseDTO.builder()
                .countryCode(countryCode)
                .name(name)
                .build();
    }
}
