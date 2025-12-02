package com.kiwadev.mocktest.service;

import com.kiwadev.mocktest.models.domain.User;
import com.kiwadev.mocktest.models.web.RegisterRequestDTO;
import com.kiwadev.mocktest.models.web.UserResponseDTO;
import com.kiwadev.mocktest.repository.UserRepository;
import com.kiwadev.mocktest.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldReturnUserWhenIdExists() {
        User mockUser = new User(1L, "Alice", "alice", "123456", LocalDate.now());
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        UserResponseDTO result = userService.findById(1L);

        assertEquals("Alice", result.getName());
        verify(userRepository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.findById(99L));

        assertEquals("User not found", exception.getMessage());
    }

//    @Test
//    void shouldCreateUserSuccessfully() {
//        RegisterRequestDTO savedUser = new RegisterRequestDTO( "Alice", "alice", "123456", LocalDate.now());
//        when(userRepository.save(any(User.class))).thenReturn(savedUser);
//
//        User user = userService.register(savedUser, null);
//
//        assertEquals("Bob", user.getName());
//        verify(userRepository).save(any(User.class));
//    }

}
