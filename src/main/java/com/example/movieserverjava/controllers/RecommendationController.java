package com.example.movieserverjava.controllers;

import com.example.movieserverjava.models.Recommendation;
import com.example.movieserverjava.repositories.RecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class RecommendationController {

    @Autowired
    RecommendationRepository recommendationRepository;

    /**
     * record a recommendation in the database
     * @param from who the recommendation is from
     * @param to where the recommendation is going to
     * @param value 0 is the title, 1 is the content, 2 is the imdbid
     */
    @PutMapping ("/api/recommendation/{from}/{to}")
    public void recordRecommendation(
            @PathVariable("from") String from,
            @PathVariable("to") String to,
            @RequestBody String[] value) {
        Recommendation re = new Recommendation(value[0], value[1], from, to, value[2]);
        recommendationRepository.save(re);
    }

    /**
     * get the recommendations sent to me
     * @param username my username
     * @return a list of recommendations
     */
    @GetMapping ("/api/recommendation/get/{username}")
    public List<Recommendation> getRecommendationToMe(
            @PathVariable("username") String username) {
        List<Recommendation> re = recommendationRepository.findRecommendationByTo(username);
        if (re == null) return new ArrayList<>();
        return re;
    }

    /**
     * get the recommendations I sent
     * @param username my username
     * @return a list of recommendations I sent
     */
    @GetMapping ("/api/recommendation/send/{username}")
    public List<Recommendation> getRecommendationISent(
            @PathVariable("username") String username) {
        List<Recommendation> re =  recommendationRepository.findRecommendationByFrom(username);
        if (re == null) return new ArrayList<Recommendation>();
        return re;
    }

    /**
     * delete a recommendation I received
     * @param id the recommendation id
     */
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

    /**
     * delete a recommendation I sent
     * @param id the recommendation id
     */
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

    @GetMapping("/api/recommendation/all")
    public List<Recommendation> getAllRecommendations() {
        List<Recommendation> ls = (List<Recommendation>) recommendationRepository.findAll();
        List<Recommendation> result = new ArrayList<>();
        Set<String> saw = new HashSet<>();
        for (int i = ls.size() - 1; i >=0 ; i--) {
            if (!saw.contains(ls.get(i).getImdbid())) {
                result.add(ls.get(i));
                saw.add(ls.get(i).getImdbid());
            }
        }
        return result;
    }
}