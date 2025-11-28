package com.kiwadev.mocktest.models.domain;


import com.kiwadev.mocktest.models.web.ActorRequestDTO;
import com.kiwadev.mocktest.models.web.ActorResponseDTO;
import com.kiwadev.mocktest.models.web.MovieResponseDTO;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "actor", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Actor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id")
    private Long actorId;
    private String name;

    public ActorResponseDTO toResponse(){
        return ActorResponseDTO.builder()
                .name(name)
                .build();
    }
}
