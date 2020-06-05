package com.example.movieserverjava.controllers;

import com.example.movieserverjava.models.Recommendation;
import com.example.movieserverjava.repositories.RecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class RecommendationController {

    @Autowired
    RecommendationRepository recommendationRepository;


    @PutMapping ("/api/recommendation/{from}/{to}")
    public void recordRecommendation(
            @PathVariable("from") String from,
            @PathVariable("to") String to,
            @RequestBody String[] value) {
        Recommendation re = new Recommendation(value[0], value[1], from, to, value[2]);
        recommendationRepository.save(re);
    }

    @GetMapping ("/api/recommendation/get/{username}")
    public List<Recommendation> getRecommendationToMe(
            @PathVariable("username") String username) {
        List<Recommendation> re = recommendationRepository.findRecommendationByTo(username);
        if (re == null) return new ArrayList<>();
        return re;
    }

    @GetMapping ("/api/recommendation/send/{username}")
    public List<Recommendation> getRecommendationISent(
            @PathVariable("username") String username) {
        List<Recommendation> re =  recommendationRepository.findRecommendationByFrom(username);
        if (re == null) return new ArrayList<>();
        return re;
    }

    @DeleteMapping ("/api/recommendation/delete/from/{id}")
    public void deleteRecommendationByFrom(
            @PathVariable("id") long id) {
        Recommendation rec = recommendationRepository.findById(id).get();
        rec.setFromUsername("");
        recommendationRepository.save(rec);
        if (rec.getToUsername().equals("")) {
            recommendationRepository.delete(rec);
        }
    }

    @DeleteMapping ("/api/recommendation/delete/to/{id}")
    public void deleteRecommendationByTo(
            @PathVariable("id") long id) {
        Recommendation rec = recommendationRepository.findById(id).get();
        rec.setToUsername("");
        recommendationRepository.save(rec);
        if (rec.getFromUsername().equals("")) {
            recommendationRepository.delete(rec);
        }
    }
}