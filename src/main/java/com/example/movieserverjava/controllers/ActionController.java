package com.example.movieserverjava.controllers;

import com.example.movieserverjava.models.Action;
import com.example.movieserverjava.models.Movie;
import com.example.movieserverjava.repositories.ActionRepository;
import com.example.movieserverjava.repositories.MovieRepository;
import com.example.movieserverjava.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ActionController {

    @Autowired
    ActionRepository actionRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * record a user action to the database. currently there two types of actions: rate and comment
     * @param type the type of the action
     * @param mid the imdbid of the movie
     * @param uid the userid of the movie
     * @param value an array of Strings. first String is the value of the action. second String is the movie name,
     *              third String is username
     */
    @PutMapping ("/api/action/{type}/{mid}/{uid}")
    public void recordAction(
            @PathVariable("type") String type,
            @PathVariable("mid") String mid,
            @PathVariable("uid") long uid,
            @RequestBody String[] value) {
        if (type.equals("rate")) {
            Action action = new Action(uid, mid, type, "", Integer.parseInt(value[0]), value[2], value[1]);
            actionRepository.save(action);
        } else {
            Action action = new Action(uid, mid, type, value[0], 0, value[2], value[1]);
            actionRepository.save(action);
        }
    }

    /**
     * search for a list of actions by imdbid
     * @param imdbid the imdbid of the movie
     * @return a list of actions performed on this movie
     */
    @GetMapping("/api/action/movie/{imdbid}")
    public List<Action> findActionByMovieID(@PathVariable("imdbid") String imdbid) {
        List<Action> list = actionRepository.findActionByMovieId(imdbid);
        if (list == null) {
            return new ArrayList<Action>();
        }
        return list;
    }

    /**
     * search for a movie by userid. not sure if this is required anymore.
     * @param userid the imdbid of the movie
     * @return movie object stored in db
     */
    @GetMapping("/api/action/user/{userid}")
    public List<Action> findActionByUserID(@PathVariable("userid") long userid) {
        List<Action> list = actionRepository.findActionByUserId(userid);
        if (list == null) {
            return new ArrayList<Action>();
        }
        return list;
    }

    /**
     * return the average rating of the given movie
     * @param imdbid the imdbid of the movie
     * @return a double value of the average rating
     */
    @GetMapping("/api/action/rating/{imdbid}")
    public double averageRating(@PathVariable("imdbid") String imdbid) {
        List<Action> list = actionRepository.findRatingsByMovieId(imdbid);
        if (list == null) {
            return 0;
        }
        List<Integer> ilist = list.stream().map(Action::getRating).collect(Collectors.toList());
        double result = Math.round(ilist.stream().mapToInt(val->val).average().orElse(0.0) * 10);
        return result/10;
    }

    /**
     * return a list of comments related to the given movie
     * @param imdbid the imdbid of the movie
     * @return a list of comments related to the given movie
     */
    @GetMapping("/api/action/comments/{imdbid}")
    public List<Action> allComments(@PathVariable("imdbid") String imdbid) {
        List<Action> list = actionRepository.findCommentsByMovieId(imdbid);
        if (list == null) {
            return new ArrayList<Action>();
        }
        return list;
    }
}
