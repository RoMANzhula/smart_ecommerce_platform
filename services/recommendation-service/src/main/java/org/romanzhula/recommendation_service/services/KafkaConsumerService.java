package org.romanzhula.recommendation_service.services;

import lombok.RequiredArgsConstructor;
import org.romanzhula.recommendation_service.models.Recommendation;
import org.romanzhula.recommendation_service.repositories.RecommendationRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final RecommendationRepository recommendationRepository;

    @KafkaListener(
            topics = "user-activity-topic",
            groupId = "recommendation-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleUserActivityViewedProduct(Recommendation recommendation) {

        // for testing
//        System.out.println("Received Recommendation:");
//        System.out.println("User ID: " + recommendation.getUserId());
//        System.out.println("Product ID: " + recommendation.getProductId());
//        System.out.println("Action: " + recommendation.getAction());
//        System.out.println("Score: " + recommendation.getScore());

        recommendationRepository.save(recommendation);
    }

}
