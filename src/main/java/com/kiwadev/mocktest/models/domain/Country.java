package com.kiwadev.mocktest.models.domain;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "country", schema = "public")
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

}
