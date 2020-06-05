package com.example.movieserverjava.controllers;

import com.example.movieserverjava.models.Friendship;
import com.example.movieserverjava.models.LikeAction;
import com.example.movieserverjava.models.Movie;
import com.example.movieserverjava.models.User;
import com.example.movieserverjava.repositories.FriendshipRepository;
import com.example.movieserverjava.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class FriendshipController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FriendshipRepository friendshipRepository;

    /**
     * record a friendship
     * @param uname1 the username of the requester
     * @param uname2 the username of the receiver
     */
    @PutMapping ("/api/friendship/{uname1}/{uname2}")
    public void recordFriendship(
            @PathVariable("uname1") String uname1,
            @PathVariable("uname2") String uname2)
    {
        Friendship fs = new Friendship(uname1, uname2, false);
        User requester = userRepository.findUserByUsername(uname1);
        User receiver = userRepository.findUserByUsername(uname2);
        fs.setRequester(requester);
        fs.setReceiver(receiver);
        friendshipRepository.save(fs);
    }

    /**
     * search for a list of friendship by requester username
     * @param requester the requester username
     * @return a list of friendships requested by this user
     */
    @GetMapping("/api/friendship/request/{requester}")
    public List<Friendship> findFriendshipByRequester(@PathVariable("requester") String requester) {
        List<Friendship> list = friendshipRepository.pendingRequests(requester);
        if (list == null) {
            return new ArrayList<Friendship>();
        }
        return list;
    }

    /**
     * search for a list of friendship by receiver username
     * @param receiver the receiver username
     * @return a list of friendships received by this user
     */
    @GetMapping("/api/friendship/receive/{receiver}")
    public List<Friendship> findFriendshipByReceiver(@PathVariable("receiver") String receiver) {
        List<Friendship> list = friendshipRepository.pendingReceives(receiver);
        if (list == null) {
            return new ArrayList<Friendship>();
        }
        return list;
    }

    /**
     * search for a list of valid friendships by username
     * @param username the given username
     * @return a list of valid friendships
     */
    @GetMapping("/api/friendship/valid/{username}")
    public List<Friendship> findValidFriendships(@PathVariable("username") String username) {
        List<Friendship> list = friendshipRepository.validFriendships(username);
        if (list == null) {
            return new ArrayList<Friendship>();
        }
        return list;
    }

    /**
     * make a friendship valid
     * @param requester the requester username
     * @param receiver the receiver username
     */
    @PutMapping("/api/friendship/valid/{requester}/{receiver}")
    public void makeValid(@PathVariable("requester") String requester,
                          @PathVariable("receiver") String receiver) {
        Friendship fs = friendshipRepository.findFriendshipByRequesterAndReceiver(requester, receiver);
        fs.setValid(true);
        friendshipRepository.save(fs);
    }

    /**
     * delete a friendship
     * @param requester the requester username
     * @param receiver the receiver username
     */
    @DeleteMapping("/api/friendship/delete/{requester}/{receiver}")
    public void cancelRequest(@PathVariable("requester") String requester,
                          @PathVariable("receiver") String receiver) {
        User req = userRepository.findUserByUsername(requester);
        User rec = userRepository.findUserByUsername(receiver);

        Friendship fs = req.getRequested().stream().filter(f -> f.getReceiver().getUsername().equals(receiver)).findFirst().get();
        req.getRequested().remove(fs);
        userRepository.save(req);
        rec.getReceived().remove(fs);
        userRepository.save(rec);
        fs.setReceiver(null);
        fs.setRequester(null);
        friendshipRepository.delete(fs);
    }
}