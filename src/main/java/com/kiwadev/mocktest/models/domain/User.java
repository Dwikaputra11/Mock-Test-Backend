package com.kiwadev.mocktest.models.domain;

import com.kiwadev.mocktest.models.web.AuthRequestDTO;
import com.kiwadev.mocktest.models.web.AuthResponseDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class User implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    private String name;
    private String username;
    private String password;
    private Long dateBirth;

    @OneToMany(mappedBy = "user")
    private Set<SavedMovie> savedMovies;

    public AuthResponseDTO toAuthResponse(String accessToken){
        return AuthResponseDTO.builder()
                .name(name)
                .birthDate(dateBirth)
                .accessToken(accessToken)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
