package com.kiwadev.mocktest.controller;

import com.kiwadev.mocktest.helper.Constant;
import com.kiwadev.mocktest.helper.ResponseHandler;
import com.kiwadev.mocktest.models.web.*;
import com.kiwadev.mocktest.services.CastService;
import com.kiwadev.mocktest.services.MovieService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
@Slf4j
public class MovieController {

    private final MovieService movieService;
    private final CastService castService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        return ResponseHandler.generateResponse(
                Constant.SUCCESS_RETRIEVE_MSG,
                HttpStatus.OK,
                movieService.findById(id)
        );
    }

    @GetMapping
    public ResponseEntity<Object> findAll(
            @RequestParam String country,
            @RequestParam(required = false) Long genre,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int page_size,
            @RequestParam(required = false, defaultValue = "-year") String sort
    ) {
        Sort s = sort.startsWith("-") ?
                Sort.by(sort.substring(1)).descending()
                : Sort.by(sort).ascending();

        Pageable pageable = PageRequest.of(page - 1, page_size, s);

        return ResponseHandler.generatePagingResponse(
                Constant.SUCCESS_RETRIEVE_MSG,
                HttpStatus.OK,
                movieService.findAll(country, genre, pageable)
        );
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody MovieRequestDTO dto) {
        return ResponseHandler.generateResponse(
                Constant.SUCCESS_EDIT_MSG,
                HttpStatus.CREATED,
                movieService.create(dto)
        );
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(HttpServletRequest request, @RequestBody SavedMovieRequestDTO dto) {
        return ResponseHandler.generateResponse(
                Constant.SUCCESS_EDIT_MSG,
                HttpStatus.CREATED,
                movieService.save(request,dto)
        );
    }

    @DeleteMapping("/save")
    public ResponseEntity<Object> deleteByUser() {
        return ResponseHandler.generateResponse(
                Constant.SUCCESS_EDIT_MSG,
                HttpStatus.OK,
                null
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id,
                                         @RequestBody MovieRequestDTO dto) {
        return ResponseHandler.generateResponse(
                Constant.SUCCESS_EDIT_MSG,
                HttpStatus.OK,
                movieService.update(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        movieService.delete(id);
        return ResponseHandler.generateResponse(
                Constant.SUCCESS_EDIT_MSG,
                HttpStatus.OK,
                null
        );
    }

    @PostMapping("/{movieId}/cast")
    public ResponseEntity<Object> addCast(@PathVariable Long movieId,
                                          @RequestBody List<CastRequestDTO> dto) {
        return ResponseHandler.generateResponse(
                Constant.SUCCESS_EDIT_MSG,
                HttpStatus.CREATED,
                castService.addCast(movieId, dto)
        );
    }

    @GetMapping("/{movieId}/cast")
    public ResponseEntity<Object> getCast(@PathVariable Long movieId) {
        return ResponseHandler.generateResponse(
                Constant.SUCCESS_RETRIEVE_MSG,
                HttpStatus.OK,
                castService.getCastByMovie(movieId)
        );
    }

    @DeleteMapping("/{movieId}/cast/{actorId}")
    public ResponseEntity<Object> deleteCast(@PathVariable Long movieId,
                                             @PathVariable Long actorId) {
        castService.deleteCast(movieId, actorId);
        return ResponseHandler.generateResponse(
                Constant.SUCCESS_EDIT_MSG,
                HttpStatus.OK,
                null
        );
    }

    @GetMapping("/availability")
    public ResponseEntity<Object> getAvailability(
            @RequestParam(required = false) Long movieId,
            @RequestParam(required = false) String countryCode
    ) {
        Object data;

        if (movieId != null) {
            data = movieService.getAvailability(movieId);
        } else if (countryCode != null) {
            data = movieService.getAvailability(countryCode);
        } else {
            data = List.of(); // empty list if both null
        }

        return ResponseHandler.generateResponse(
                Constant.SUCCESS_RETRIEVE_MSG,
                HttpStatus.OK,
                data
        );
    }
}


