package com.example.movieserverjava.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class LikeAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String movieName;
    private String username;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JsonIgnore
    private User user;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Movie movie;

    public LikeAction() {
    }

    public LikeAction(String movieName, String username) {
        this.movieName = movieName;
        this.username = username;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
