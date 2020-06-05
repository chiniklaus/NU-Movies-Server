package com.example.movieserverjava.controllers;

import com.example.movieserverjava.models.LikeAction;
import com.example.movieserverjava.models.Movie;
import com.example.movieserverjava.models.User;
import com.example.movieserverjava.repositories.LikeActionRepository;
import com.example.movieserverjava.repositories.MovieRepository;
import com.example.movieserverjava.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class LikeActionController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LikeActionRepository likeActionRepository;

    @Autowired
    MovieRepository movieRepository;

    /**
     * record the action that a user liked a movie
     * @param mid the imdbid of the movie
     * @param uid the userid of the movie
     * @param value an array of Strings. first String is the username. second String is the movie name,
     */
    @PutMapping ("/api/likeAction/{mid}/{uid}")
    public void recordLikeAction(
            @PathVariable("mid") String mid,
            @PathVariable("uid") long uid,
            @RequestBody String[] value) {
        LikeAction likeAction = new LikeAction(value[1], value[0]);
        User user = userRepository.findById(uid).get();
        Movie movie = movieRepository.findMovieById(mid);
        if (user.getLikeActions().stream().noneMatch(la -> la.getMovie().getImdbid().equals(mid))) {
            likeAction.setUser(user);
            likeAction.setMovie(movie);
            likeActionRepository.save(likeAction);
        }
    }

    /**
     * search for a list of likeActions by imdbid
     * @param imdbid the imdbid of the movie
     * @return a list of likeActions performed on this movie
     */
    @GetMapping("/api/likeAction/movie/{imdbid}")
    public List<LikeAction> findLikeActionByMovieID(@PathVariable("imdbid") String imdbid) {
        Movie movie = movieRepository.findMovieById(imdbid);
        List<LikeAction> list = likeActionRepository.findLikeActionByMovieId(movie.getId());
        if (list == null) {
            return new ArrayList<LikeAction>();
        }
        return list;
    }

    /**
     * find all the likeActions the given user owns
     * @param userid the given user's id
     * @return the list of likeActions the user owns
     */
    @GetMapping("/api/likeAction/user/{userid}")
    public List<LikeAction> findLikeActionByUserID(@PathVariable("userid") long userid) {
        List<LikeAction> list = likeActionRepository.findLikeActionByUserId(userid);
        if (list == null) {
            return new ArrayList<LikeAction>();
        }
        return list;
    }

    /**
     * delete an likeaction by username and imdbid
     * @param username given username
     * @param imdbid given imdbid
     */
    @DeleteMapping("/api/likeAction/delete/{username}/{imdbid}")
    public void deleteLikeActionByUername(@PathVariable("username") String username,
                                          @PathVariable("imdbid") String imdbid) {
        Movie movie = movieRepository.findMovieById(imdbid);

        LikeAction l = movie.getLikeActions().stream().filter(la -> la.getUsername().equals(username)).findFirst().get();
        movie.getLikeActions().remove(l);
        movieRepository.save(movie);
        User user = userRepository.findUserByUsername(username);
        user.getLikeActions().remove(l);
        userRepository.save(user);
        l.setUser(null);
        l.setMovie(null);
        likeActionRepository.delete(l);
    }
}
