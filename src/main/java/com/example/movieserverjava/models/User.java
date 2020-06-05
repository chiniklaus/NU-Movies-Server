package com.example.movieserverjava.models;

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

    @Lob
    private byte[] profileImage;

    @OneToMany(mappedBy = "user")
    private Set<LikeAction> likeActions;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "favAction_id", referencedColumnName = "id")
    private FavAction favorite;

    @OneToMany(mappedBy = "requester")
    private Set<Friendship> requested;

    @OneToMany(mappedBy = "receiver")
    private Set<Friendship> received;

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
     * a constructor that takes in profileImage
     * @param profileImage given profile image data
     */
    public User(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    /**
     * add the given like action to the likeAction set
     * @param la the given like action
     */
    public void addToLikes(LikeAction la) {
        this.likeActions.add(la);
    }

    /**
     * add the given friendship to the requested set
     * @param fs given friendship
     */
    public void addToRequested(Friendship fs) {this.requested.add(fs);}

    /**
     * add the given friendship to the received set
     * @param fs given friendship
     */
    public void addToReceived(Friendship fs) {this.received.add(fs);}

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

    public Set<Friendship> getRequested() {
        return requested;
    }

    public void setRequested(Set<Friendship> requested) {
        this.requested = requested;
    }

    public Set<Friendship> getReceived() {
        return received;
    }

    public void setReceived(Set<Friendship> received) {
        this.received = received;
    }
}
