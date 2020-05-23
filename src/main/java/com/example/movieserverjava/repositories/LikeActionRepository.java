package com.example.movieserverjava.repositories;

import com.example.movieserverjava.models.LikeAction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface LikeActionRepository
        extends CrudRepository<LikeAction, Long> {
    @Query(value = "select * from like_action where movie_id=:id", nativeQuery = true)
    public List<LikeAction> findLikeActionByMovieId(
            @Param("id") long id);

    @Query(value = "select * from like_action where user_id=:user_id", nativeQuery = true)
    public List<LikeAction> findLikeActionByUserId(
            @Param("user_id") long user_id);
}