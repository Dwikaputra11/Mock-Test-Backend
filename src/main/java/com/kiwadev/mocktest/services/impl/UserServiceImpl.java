package com.kiwadev.mocktest.services.impl;

import com.kiwadev.mocktest.exception.ErrorCode;
import com.kiwadev.mocktest.exception.NotFoundException;
import com.kiwadev.mocktest.models.domain.User;
import com.kiwadev.mocktest.models.web.*;
import com.kiwadev.mocktest.repository.SavedMovieRepository;
import com.kiwadev.mocktest.repository.UserRepository;
import com.kiwadev.mocktest.security.JwtService;
import com.kiwadev.mocktest.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import static com.kiwadev.mocktest.helper.Constant.REFRESH_TOKEN_HEADER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final JwtService            jwtService;
    private final AuthenticationManager authManager;
    private final UserRepository        repository;
    private final PasswordEncoder       passwordEncoder;
    private final SavedMovieRepository  savedMovieRepository;

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public AuthResponseDTO authenticate(AuthRequestDTO auth, HttpServletResponse response) {
        if (auth.getPassword() == null) throw new RuntimeException("Password is required");
        if (auth.getUsername() == null) throw new RuntimeException("Email is required");

        try {
            Authentication authData = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            auth.getUsername(),
                            auth.getPassword()
                    )
            );

            var user = (User) authData.getPrincipal();
            if (user == null) throw new RuntimeException("User not found.");

            var claims       = createClaimsJwt(user.getName(), user.getDateBirth());
            var accessToken  = jwtService.generateToken(claims, user);
            var refreshToken = jwtService.generateRefreshToken(claims, user);

            response.addHeader(REFRESH_TOKEN_HEADER, refreshToken);


            return user.toAuthResponse(accessToken);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Email or password is wrong. Please try again!");
        } catch (AuthenticationException e) {
            throw new RuntimeException("Authentication Failed: " + e.getMessage());
        }
    }

    @Override
    public UserResponseDTO findById(Long id) {
        var user = repository.findById(id).orElseThrow(() -> new RuntimeException("User id : " + id + " not found."));
        return user.toUserResponse();
    }

    @Override
    public AuthResponseDTO register(RegisterRequestDTO user, HttpServletResponse response) {
        if (user.getName().isEmpty() || user.getDateBirth() == null) {
            throw new RuntimeException("Data user is not valid");
        }

        var isUsernameExist = repository.findByUsername(user.getUsername()).isPresent();

        if (isUsernameExist) throw new RuntimeException("Username already exist");

        var password = passwordEncoder.encode(user.getPassword());
        var newUser = User.builder()
                .password(password)
                .username(user.getUsername())
                .dateBirth(user.getDateBirth())
                .name(user.getName())
                .build();

        repository.save(newUser);

        var claims       = createClaimsJwt(user.getName(), user.getDateBirth());
        var accessToken  = jwtService.generateToken(claims, newUser);
        var refreshToken = jwtService.generateRefreshToken(claims, newUser);

        response.setHeader(REFRESH_TOKEN_HEADER, refreshToken);
        return newUser.toAuthResponse(accessToken);
    }

    @Override
    public UserResponseDTO update(Long id, UserRequestDTO user) {
        if (user.getName().isEmpty() || user.getBirthDate() == null) {
            throw new RuntimeException("Data user is not valid");
        }

        User u = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        u.setDateBirth(user.getBirthDate());
        u.setName(user.getName());

        repository.save(u);
        return u.toUserResponse();
    }

    @Override
    public List<MovieResponseDTO> allMovies(HttpServletRequest request) {
        var token   = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        if(token.isEmpty()) throw new RuntimeException("Invalid authorization: token is empty");
        var username = jwtService.extractUsername(token);

        var user = repository.findByUsername(username).orElseThrow(() -> new RuntimeException("user not exist"));

        return savedMovieRepository.findByUser_UserId(user.getUserId()).stream().map(d ->
                MovieResponseDTO.builder()
                        .movieId(d.getMovie().getMovieId())
                        .genreName(d.getMovie().getGenre().getName())
                        .year(d.getMovie().getYear())
                        .title(d.getMovie().getTitle())
                        .build()
        ).toList();
    }

    @Override
    public void deleteMovie(HttpServletRequest request, Long movieId) {
        var token   = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        if(token.isEmpty()) throw new RuntimeException("Invalid authorization: token is empty");
        var username = jwtService.extractUsername(token);

        var user = repository.findByUsername(username).orElseThrow(() -> new RuntimeException("user not exist"));

        var savedMovie = savedMovieRepository.findByUser_UserIdAndMovie_MovieId(user.getUserId(), movieId).orElseThrow(() -> new NotFoundException("not save", ErrorCode.NOT_SAVE));

        savedMovieRepository.delete(savedMovie);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id))
            throw new RuntimeException("User id " + id + " not found");
        repository.deleteById(id);
    }

    HashMap<String, Object> createClaimsJwt(String name, LocalDate birthDate) {
        var claims = new HashMap<String, Object>();
        claims.put("name", name);
        claims.put("birth_date", birthDate.toString());
        return claims;
    }
}
