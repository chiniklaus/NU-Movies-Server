package com.example.movieserverjava.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String imdbid;
    private String name;
    private String poster;

    @OneToMany(mappedBy = "movie")
    @JsonIgnore
    private Set<LikeAction> likeActions;

    @OneToMany(mappedBy = "movie")
    @JsonIgnore
    private Set<FavAction> favActions;

    /**
     * empty constructor
     */
    public Movie() {
    }

    /**
     * a constructor that takes in movie name and director
     * @param name movie name
     * @param imdbid movie's imdbID
     */
    public Movie(String name, String imdbid, String poster) {
        this.name = name;
        this.imdbid = imdbid;
        this.poster = poster;
    }

    public void addToFavAction(FavAction ac) {
        this.favActions.add(ac);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getImdbid() {
        return imdbid;
    }

    public void setImdbid(String imdbid) {
        this.imdbid = imdbid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}