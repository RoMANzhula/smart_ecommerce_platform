package org.romanzhula.order_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void publishOrderCreatedEvent(String orderId) {
        kafkaTemplate.send("order-created-topic", orderId);
    }

}
