package com.kiwadev.mocktest.services;

import com.kiwadev.mocktest.models.domain.User;
import com.kiwadev.mocktest.models.web.AuthRequestDTO;
import com.kiwadev.mocktest.models.web.AuthResponseDTO;
import com.kiwadev.mocktest.models.web.RegisterRequestDTO;
import com.kiwadev.mocktest.models.web.UpdateUserRequestDTO;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface UserService {
    List<User> findAll();
    AuthResponseDTO authenticate(AuthRequestDTO auth, HttpServletResponse response);
    User findById(Long id);
    User save(RegisterRequestDTO user);
    User update(Long id,UpdateUserRequestDTO user);
    void delete(Long id);
}
