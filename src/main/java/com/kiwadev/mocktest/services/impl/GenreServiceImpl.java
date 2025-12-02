package com.kiwadev.mocktest.services.impl;

import com.kiwadev.mocktest.exception.ErrorCode;
import com.kiwadev.mocktest.exception.NotFoundException;
import com.kiwadev.mocktest.models.domain.Genre;
import com.kiwadev.mocktest.models.domain.Movie;
import com.kiwadev.mocktest.models.web.ActorRequestDTO;
import com.kiwadev.mocktest.models.web.ActorResponseDTO;
import com.kiwadev.mocktest.models.web.GenreRequestDTO;
import com.kiwadev.mocktest.models.web.GenreResponseDTO;
import com.kiwadev.mocktest.repository.GenreRepository;
import com.kiwadev.mocktest.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;


    @Override
    public Page<GenreResponseDTO> findAll(Pageable pageable) {
        return genreRepository.findAll(pageable).map(Genre::toResponse);
    }

    @Override
    public GenreResponseDTO findById(Long id) {
        return genreRepository.findById(id)
                .map(Genre::toResponse)
                .orElseThrow(() -> new NotFoundException("Genre not found", ErrorCode.GENRE_NOT_FOUND));
    }

    @Override
    public GenreResponseDTO create(GenreRequestDTO genre) {

        if(genre.getName().isEmpty()){
            throw new RuntimeException("Name field is empty");
        }

        Genre newGenre = Genre.builder()
                .name(genre.getName())
                .build();

        genreRepository.save(newGenre);

        return newGenre.toResponse();
    }

    @Override
    public GenreResponseDTO update(Long id, GenreRequestDTO genre) {
        if(id == null || id == 0){
            throw new RuntimeException("Id cannot be null or empty");
        }
        if(genre.getName().isEmpty()){
            throw new RuntimeException("Name field is empty");
        }

        Genre savedGenre = genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Genre not found", ErrorCode.GENRE_NOT_FOUND));

        savedGenre.setName(genre.getName());
        genreRepository.save(savedGenre);

        return savedGenre.toResponse();
    }

    @Override
    public void delete(Long id) {
        genreRepository.deleteById(id);
    }
}
