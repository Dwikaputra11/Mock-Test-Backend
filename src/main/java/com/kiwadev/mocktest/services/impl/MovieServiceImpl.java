package com.kiwadev.mocktest.services.impl;

import com.kiwadev.mocktest.exception.ConflictException;
import com.kiwadev.mocktest.exception.ErrorCode;
import com.kiwadev.mocktest.exception.NotFoundException;
import com.kiwadev.mocktest.exception.UnprocessEntityException;
import com.kiwadev.mocktest.models.domain.*;
import com.kiwadev.mocktest.models.web.*;
import com.kiwadev.mocktest.repository.*;
import com.kiwadev.mocktest.security.JwtService;
import com.kiwadev.mocktest.services.MovieService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final CountryRepository countryRepository;

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final CastRepository  castRepository;
    private final UserRepository  userRepository;
    private final MovieAvailabilityRepository availabilityRepository;
    private final SavedMovieRepository savedMovieRepository;
    private final JwtService           jwtService;



    @Override
    public MovieResponseDTO create(MovieRequestDTO dto) {
        Genre genre = genreRepository.findById(dto.getGenreId())
                .orElseThrow(() -> new NotFoundException("Genre not found", ErrorCode.GENRE_NOT_FOUND));

        Movie movie = Movie.builder()
                .genre(genre)
                .title(dto.getTitle())
                .year(dto.getYear())
                .build();

        movieRepository.save(movie);

        return movie.toMovieResponse();
    }

    @Override
    public MovieResponseDTO save(HttpServletRequest request, SavedMovieRequestDTO dto) {
        if(dto.getCountry().isEmpty() || dto.getMovieId() == 0 || dto.getUserId() == 0) throw new RuntimeException("Data should not be null");

        var userValid = userMatch(request, dto.getUserId());

        if(!userValid) throw new RuntimeException("User cannot save movie to other user");

        var user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not exist"));
        var movie = movieRepository.findById(dto.getMovieId()).orElseThrow(() -> new RuntimeException("Movie not exist"));

        String countryCode = dto.getCountry();
        if(!countryRepository.existsById(countryCode)){
            throw new RuntimeException("Country code not exist");
        }

        var available = availabilityRepository.existsByMovie_MovieIdAndCountry_CountryCode(movie.getMovieId(), countryCode);

        if(!available) throw new UnprocessEntityException("Movie not available in the country", ErrorCode.UNAVAILABLE_IN_COUNTRY);

        if (savedMovieRepository.existsByUser_UserIdAndMovie_MovieId(user.getUserId(), movie.getMovieId())) {
            throw new ConflictException("Movie already saved by this user", ErrorCode.DUPLICATE_SAVE);
        }

        SavedMovie s = SavedMovie.builder()
                .dateCreated(LocalDate.now())
                .id(new SavedMovieId(user.getUserId(), movie.getMovieId()))
                .movie(Movie.builder().movieId(movie.getMovieId()).build())
                .user(User.builder().userId(user.getUserId()).build())
                .build();

        savedMovieRepository.save(s);

        return movie.toMovieResponse();
    }

    @Override
    public MovieResponseDTO update(Long movieId, MovieRequestDTO dto) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Genre genre = genreRepository.findById(dto.getGenreId())
                .orElseThrow(() -> new RuntimeException("Genre not found"));

        movie.setGenre(genre);
        movie.setTitle(dto.getTitle());
        movie.setYear(dto.getYear());

        movieRepository.save(movie);

        return movie.toMovieResponse();
    }

    @Override
    public void delete(Long movieId) {
        movieRepository.deleteById(movieId);
    }

    @Override
    public MovieResponseDTO findById(Long movieId) {
        return movieRepository.findById(movieId)
                .map(Movie::toMovieResponse)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
    }

    @Override
    public Page<MovieResponseDTO> findAll(String country,Long genre, Pageable pageable) {
        if(country.isEmpty()){
            throw new RuntimeException("Country should be filled");
        }

        return movieRepository.findAllFiltered(country, genre, pageable).map(Movie::toMovieResponse);
    }

    @Override
    public List<CastResponseDTO> getMovieCast(Long movieId) {
        return castRepository.findByMovie_MovieId(movieId)
                .stream()
                .map(Cast::toResponse).toList();
    }

    @Override
    public List<MovieAvailabilityResponseDTO> getAvailability(Long movieId) {
        return availabilityRepository.findByMovie_MovieId(movieId)
                .stream()
                .map(MovieAvailability::toResponse).toList();
    }

    @Override
    public List<MovieResponseDTO> getSavedMovie(Long userId) {
        return  null;
//        return savedMovieRepository.findByUser_UserId(userId)
//                .stream()
//                .map(saved -> saved.getMovie().toMovieResponse())
//                .toList();
    }

    @Override
    public List<MovieAvailabilityResponseDTO> getAvailability(String countryCode) {
        return availabilityRepository.findByCountry_CountryCode(countryCode)
                .stream()
                .map(MovieAvailability::toResponse).toList();
    }


    private Boolean userMatch(HttpServletRequest request, Long userId){
        var token   = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        if(token.isEmpty()) throw new RuntimeException("Invalid authorization: token is empty");
        var username   = jwtService.extractUsername(token);
        return userRepository.existsByUserIdAndUsername(userId, username);
    }

}
