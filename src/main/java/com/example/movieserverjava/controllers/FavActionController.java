package com.example.movieserverjava.controllers;

import com.example.movieserverjava.models.FavAction;
import com.example.movieserverjava.models.Movie;
import com.example.movieserverjava.models.User;
import com.example.movieserverjava.repositories.FavActionRepository;
import com.example.movieserverjava.repositories.MovieRepository;
import com.example.movieserverjava.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class FavActionController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FavActionRepository favActionRepository;

    @Autowired
    MovieRepository movieRepository;

    /**
     * record the action that a user selected a movie as favorite
     * @param mid the imdbid of the movie
     * @param uid the userid of the movie
     * @param value an array of Strings. first String is the username. second String is the movie name,
     */
    @PutMapping ("/api/favAction/{mid}/{uid}")
    public void recordFavAction(
            @PathVariable("mid") String mid,
            @PathVariable("uid") long uid,
            @RequestBody String[] value) {
        FavAction favAction = new FavAction(value[1], value[0]);
        User user = userRepository.findById(uid).get();
        Movie movie = movieRepository.findMovieById(mid);
        user.setFavorite(favAction);
        favAction.setMovie(movie);
        userRepository.save(user);
        favActionRepository.save(favAction);
    }

    /**
     * search for a list of favActions by imdbid
     * @param imdbid the imdbid of the movie
     * @return a list of favActions performed on this movie
     */
    @GetMapping("/api/favAction/movie/{imdbid}")
    public List<FavAction> findFavActionByMovieID(@PathVariable("imdbid") String imdbid) {
        Movie movie = movieRepository.findMovieById(imdbid);
        List<FavAction> list = favActionRepository.findFavActionByMovieId(movie.getId());
        if (list == null) {
            return new ArrayList<FavAction>();
        }
        return list;
    }
}
