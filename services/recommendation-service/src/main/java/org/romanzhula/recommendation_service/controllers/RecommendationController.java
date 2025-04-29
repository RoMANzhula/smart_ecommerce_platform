package org.romanzhula.recommendation_service.controllers;

import lombok.RequiredArgsConstructor;
import org.romanzhula.recommendation_service.models.Recommendation;
import org.romanzhula.recommendation_service.services.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;


    @GetMapping("/{user-id}")
    public ResponseEntity<List<Recommendation>> getAllRecommendationsByUserId(
            @PathVariable("user-id") String userId
    ) {
        return ResponseEntity.ok(recommendationService.getRecommendationForUser(userId));
    }

}
