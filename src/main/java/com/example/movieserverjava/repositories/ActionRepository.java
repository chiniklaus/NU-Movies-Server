package com.example.movieserverjava.repositories;

import com.example.movieserverjava.models.Action;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ActionRepository
        extends CrudRepository<Action, Long> {
    @Query(value = "select * from action where movie_id=:imdbid", nativeQuery = true)
    public List<Action> findActionByMovieId(
            @Param("imdbid") String imdbid);

    @Query(value = "select * from action where user_id=:user_id", nativeQuery = true)
    public List<Action> findActionByUserId(
            @Param("user_id") long user_id);

    @Query(value = "select * from action where movie_id=:imdbid and type = 'rate'", nativeQuery = true)
    public List<Action> findRatingsByMovieId(
            @Param("imdbid") String imdbid);

    @Query(value = "select * from action where movie_id=:imdbid and type = 'comment'", nativeQuery = true)
    public List<Action> findCommentsByMovieId(
            @Param("imdbid") String imdbid);
}