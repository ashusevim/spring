package com.springarena.service;

import com.springarena.model.Movie;
import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<Movie> getAllMovies();
    Optional<Movie> getMovieById(Long id);
    Movie createMovie(Movie movie);
    Optional<Movie> updateMovie(Long id, Movie movie);
    boolean deleteMovie(Long id);
}
