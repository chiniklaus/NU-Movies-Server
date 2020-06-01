package com.example.movieserverjava.models;

import javax.persistence.*;

/**
 * An action is either a rating or a comment
 */
@Entity
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long userId;
    private String userName;
    private String movieId;
    private String movieName;
    private String type;
    private String comment;
    private int rating;
    private String date;

    public Action() {
    }

    public Action(long userId,
                  String movieId,
                  String type,
                  String comment,
                  int rating,
                  String userName,
                  String movieName,
                  String date) {
        this.userId = userId;
        this.movieId = movieId;
        this.type = type;
        this.comment = comment;
        this.rating = rating;
        this.userName = userName;
        this.movieName = movieName;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
}
