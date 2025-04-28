package org.romanzhula.product_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void publishProductCreatedEvent(String productId) {
        kafkaTemplate.send("product-created-topic", productId);
    }

}
