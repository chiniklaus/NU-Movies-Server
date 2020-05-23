package com.example.movieserverjava.controllers;

import com.example.movieserverjava.models.Movie;
import com.example.movieserverjava.models.User;
import com.example.movieserverjava.repositories.MovieRepository;
import com.example.movieserverjava.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class MovieController {
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * search for a movie by imdbid
     * @param imdbid the imdbid of the movie
     * @return movie object stored in db
     */
    @GetMapping("/api/movie/{imdbid}")
    public Movie findMovieByID(@PathVariable("imdbid") String imdbid) {
        Movie dbmovie = movieRepository.findMovieById(imdbid);
        if (dbmovie == null) {
            return new Movie();
        }
        return dbmovie;
    }

    /**
     * saving a movie in the movie repository
     * @param movie the given movie to be saved
     */
    @PutMapping("/api/movie/create")
    public void createMovie(@RequestBody Movie movie) {
        if (movieRepository.findMovieById(movie.getImdbid()) == null) movieRepository.save(movie);
    }
}
