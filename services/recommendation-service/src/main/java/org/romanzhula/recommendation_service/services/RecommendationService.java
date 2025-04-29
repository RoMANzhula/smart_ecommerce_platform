package org.romanzhula.recommendation_service.services;

import lombok.RequiredArgsConstructor;
import org.romanzhula.recommendation_service.models.Recommendation;
import org.romanzhula.recommendation_service.repositories.RecommendationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;


    @Transactional(readOnly = true)
    public List<Recommendation> getRecommendationForUser(String userId) {
        return recommendationRepository.findByUserId(userId);
    }

}
