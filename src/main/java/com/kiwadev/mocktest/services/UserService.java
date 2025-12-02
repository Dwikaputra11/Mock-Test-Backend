package com.kiwadev.mocktest.services;

import com.kiwadev.mocktest.models.domain.User;
import com.kiwadev.mocktest.models.web.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    List<User> findAll();
    AuthResponseDTO authenticate(AuthRequestDTO auth, HttpServletResponse response);
    UserResponseDTO findById(Long id);
    AuthResponseDTO register(RegisterRequestDTO user, HttpServletResponse response);
    UserResponseDTO update(Long id,UserRequestDTO user);
    List<MovieResponseDTO> allMovies(HttpServletRequest request);
    void deleteMovie(HttpServletRequest request, Long movieId);
    void delete(Long id);
}
