package com.example.movieserverjava.repositories;

import com.example.movieserverjava.models.Recommendation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface RecommendationRepository
        extends CrudRepository<Recommendation, Long> {
    @Query(value = "select * from recommendation where from_username=:from", nativeQuery = true)
    public List<Recommendation> findRecommendationByFrom(
            @Param("from") String from);

    @Query(value = "select * from recommendation where to_username=:to", nativeQuery = true)
    public List<Recommendation> findRecommendationByTo(
            @Param("to") String to);
}