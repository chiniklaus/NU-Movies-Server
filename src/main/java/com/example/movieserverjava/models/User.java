package com.example.movieserverjava.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private String type;

    @OneToMany(mappedBy = "user")
    private Set<LikeAction> likeActions;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "favAction_id", referencedColumnName = "id")
    private FavAction favorite;

    /**
     * empty constructor
     */
    public User() {
    }

    /**
     * constructor
     * @param username username of the user
     * @param password password of the user
     * @param type admin or regular user
     */
    public User(long id, String username, String password, String type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.type = type;
        this.likeActions = new HashSet<>();
    }

    /**
     * add the given like action to the likeAction set
     * @param la the given like action
     */
    public void addToLikes(LikeAction la) {
        this.likeActions.add(la);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<LikeAction> getLikeActions() {
        return likeActions;
    }

    public void setLikeActions(Set<LikeAction> likeActions) {
        this.likeActions = likeActions;
    }

    public FavAction getFavorite() {
        return favorite;
    }

    public void setFavorite(FavAction favorite) {
        this.favorite = favorite;
    }
}
