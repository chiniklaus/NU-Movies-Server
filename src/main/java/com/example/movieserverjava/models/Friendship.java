package com.example.movieserverjava.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * A friendship between two users
 */
@Entity
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String requesterName;

    private String receiverName;

    private boolean valid;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JsonIgnore
    private User requester;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JsonIgnore
    private User receiver;

    public Friendship() {
    }

    /**
     * public constructor
     * @param requesterName username of the requester
     * @param receiverName  username of the receiver
     */
    public Friendship(String requesterName, String receiverName, boolean valid) {
        this.requesterName = requesterName;
        this.receiverName = receiverName;
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}
