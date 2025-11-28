package com.kiwadev.mocktest.services.impl;

import com.kiwadev.mocktest.models.domain.User;
import com.kiwadev.mocktest.models.web.AuthRequestDTO;
import com.kiwadev.mocktest.models.web.AuthResponseDTO;
import com.kiwadev.mocktest.models.web.RegisterRequestDTO;
import com.kiwadev.mocktest.models.web.UpdateUserRequestDTO;
import com.kiwadev.mocktest.repository.UserRepository;
import com.kiwadev.mocktest.security.JwtService;
import com.kiwadev.mocktest.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.kiwadev.mocktest.helper.Constant.REFRESH_TOKEN_HEADER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final JwtService            jwtService;
    private final AuthenticationManager authManager;
    private final UserRepository repository;

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public AuthResponseDTO authenticate(AuthRequestDTO auth, HttpServletResponse response) {
        if(auth.getPassword() == null) throw new RuntimeException("Password is required");
        if(auth.getUsername() == null) throw new RuntimeException("Email is required");

        try{
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            auth.getUsername(),
                            auth.getPassword()
                    )
            );
            var user            = repository.findByUsername(auth.getUsername()).orElseThrow(() -> new RuntimeException("User not found."));
            var claims          = createClaimsJwt(user.getName(), user.getDateBirth());
            var accessToken     = jwtService.generateToken(claims,user);
            var refreshToken    = jwtService.generateRefreshToken(claims,user);

            response.addHeader(REFRESH_TOKEN_HEADER, refreshToken);


            return user.toAuthResponse(accessToken);
        }catch(BadCredentialsException e){
            throw new RuntimeException("Email or password is wrong. Please try again!");
        }catch (AuthenticationException e){
            throw new RuntimeException("Authentication Failed: " + e.getMessage());
        }
    }

    @Override
    public User findById(Long id) {
        var user = repository.findById(id);
        if (user.isEmpty()) throw new RuntimeException("User id : " + id + " not found.");
        return user.get();
    }

    @Override
    public User save(RegisterRequestDTO user) {
        if(user.getName().isEmpty() || user.getDateBirth() == null){
            throw new RuntimeException("Data user is not valid");
        }
        User newUser = new User(null, user.name, user.username, user.password, user.dateBirth, Set.of());
        return repository.save(newUser);
    }

    @Override
    public User update(Long id, UpdateUserRequestDTO user) {
        if(user.getName().isEmpty() || user.getDateBirth() == null){
            throw new RuntimeException("Data user is not valid");
        }

        return  repository.findById(id)
                .map(existing -> {
                    existing.setName(user.getName());
                    existing.setDateBirth(user.getDateBirth());
                    return  repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void delete(Long id) {
        if(!repository.existsById(id))
            throw new RuntimeException("User id "+id+" not found");
        repository.deleteById(id);
    }

    HashMap<String, Object> createClaimsJwt(String name, Long birthDate){
        var claims = new HashMap<String, Object>();
        claims.put("name", name);
        claims.put("birth_data", birthDate);
        return claims;
    }
}
