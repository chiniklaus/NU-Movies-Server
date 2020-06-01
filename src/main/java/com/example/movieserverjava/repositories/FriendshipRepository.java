package com.example.movieserverjava.repositories;

import com.example.movieserverjava.models.Friendship;
import com.example.movieserverjava.models.LikeAction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface FriendshipRepository
        extends CrudRepository<Friendship, Long> {
    @Query(value = "select * from friendship where requester_name=:name and valid = false", nativeQuery = true)
    public List<Friendship> pendingRequests(
            @Param("name") String name);

    @Query(value = "select * from friendship where receiver_name=:name and valid = false", nativeQuery = true)
    public List<Friendship> pendingReceives(
            @Param("name") String name);

    @Query(value = "select * from friendship where (receiver_name=:name or requester_name=:name) and valid = true", nativeQuery = true)
    public List<Friendship> validFriendships(
            @Param("name") String name);

    @Query(value = "select * from friendship where requester_name=:requester and receiver_name=:receiver", nativeQuery = true)
    public Friendship findFriendshipByRequesterAndReceiver(
            @Param("requester") String requester,
            @Param("receiver") String receiver);
}