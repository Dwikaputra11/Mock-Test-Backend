package com.kiwadev.mocktest.controller;

import com.kiwadev.mocktest.helper.Constant;
import com.kiwadev.mocktest.helper.ResponseHandler;
import com.kiwadev.mocktest.models.web.GenreRequestDTO;
import com.kiwadev.mocktest.models.web.GenreResponseDTO;
import com.kiwadev.mocktest.services.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor
@Slf4j
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        return ResponseHandler.generateResponse(
                Constant.SUCCESS_RETRIEVE_MSG,
                HttpStatus.OK,
                genreService.findById(id)
        );
    }

    @GetMapping
    public ResponseEntity<Object> findAll(Pageable pageable) {
        return ResponseHandler.generatePagingResponse(
                Constant.SUCCESS_RETRIEVE_MSG,
                HttpStatus.OK,
                genreService.findAll(pageable)
        );
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody GenreRequestDTO dto) {
        return ResponseHandler.generateResponse(
                Constant.SUCCESS_EDIT_MSG,
                HttpStatus.CREATED,
                genreService.create(dto)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id,
                                         @RequestBody GenreRequestDTO dto) {
        return ResponseHandler.generateResponse(
                Constant.SUCCESS_EDIT_MSG,
                HttpStatus.OK,
                genreService.update(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        genreService.delete(id);
        return ResponseHandler.generateResponse(
                Constant.SUCCESS_EDIT_MSG,
                HttpStatus.OK,
                null
        );
    }
}
