package com.example.movieserverjava.repositories;

import com.example.movieserverjava.models.FavAction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface FavActionRepository
        extends CrudRepository<FavAction, Long> {
    @Query(value = "select * from fav_action where movie_id=:id", nativeQuery = true)
    public List<FavAction> findFavActionByMovieId(
            @Param("id") long id);

    @Query(value = "select * from fav_action where user_id=:user_id", nativeQuery = true)
    public List<FavAction> findFavActionByUserId(
            @Param("user_id") long user_id);
}