package com.springarena.service;

import com.springarena.model.Movie;
import com.springarena.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> getAllMovies() {
        // TODO: Get all movies
        return null;
    }

    @Override
    public Optional<Movie> getMovieById(Long id) {
        // TODO: Get movie by id
        return Optional.empty();
    }

    @Override
    public Movie createMovie(Movie movie) {
        // TODO: Create movie
        return null;
    }

    @Override
    public Optional<Movie> updateMovie(Long id, Movie movie) {
        // TODO: Update movie
        return Optional.empty();
    }

    @Override
    public boolean deleteMovie(Long id) {
        // TODO: Delete movie
        return false;
    }
}
