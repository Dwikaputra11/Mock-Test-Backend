package com.kiwadev.mocktest.controller;

import com.kiwadev.mocktest.helper.Constant;
import com.kiwadev.mocktest.helper.ResponseHandler;
import com.kiwadev.mocktest.models.web.*;
import com.kiwadev.mocktest.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;


    @PostMapping("/login")
    public ResponseEntity<Object> login(
            @RequestBody AuthRequestDTO request,
            HttpServletResponse response
    ){
        return ResponseHandler.generateResponse(Constant.SUCCESS_RETRIEVE_MSG, HttpStatus.OK, service.authenticate(request, response));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequestDTO request, HttpServletResponse response){
        return ResponseHandler.generateResponse(Constant.SUCCESS_RETRIEVE_MSG, HttpStatus.OK, service.register(request, response));
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<Object> profile(
            HttpServletRequest request,
            @PathVariable("id") Long id
    ){
        return ResponseHandler.generateResponse(Constant.SUCCESS_RETRIEVE_MSG, HttpStatus.OK, service.findById(id));
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<Object> profile(
            HttpServletRequest request,
            @PathVariable("id") Long id,
            @RequestBody UserRequestDTO user
            ){
        return ResponseHandler.generateResponse(Constant.SUCCESS_RETRIEVE_MSG, HttpStatus.OK, service.update(id, user));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteUser(
            @PathVariable("id") Long id
    ) {
        service.delete(id);
        return ResponseHandler.generateResponse(Constant.SUCCESS_RETRIEVE_MSG, HttpStatus.OK,id);
    }

    @GetMapping("/movies")
    public ResponseEntity<Object> userMovies(
            HttpServletRequest request
    ){
        return ResponseHandler.generateResponse(Constant.SUCCESS_RETRIEVE_MSG, HttpStatus.OK, service.allMovies(request));
    }

    @DeleteMapping("/movies/{movieId}")
    public ResponseEntity<Object> deleteMovie(
            HttpServletRequest request,
            @PathVariable("movieId") Long id
    ){
        service.deleteMovie(request, id);
        return ResponseHandler.generateResponse(Constant.SUCCESS_RETRIEVE_MSG, HttpStatus.OK, null);
    }
}
