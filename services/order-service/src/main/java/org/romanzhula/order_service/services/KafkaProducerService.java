package org.romanzhula.order_service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.romanzhula.order_service.models.events.OrderCreatedEvent;
import org.romanzhula.order_service.models.events.UserActivityEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;


    public void publishOrderCreatedEvent(String orderId, String userId) {
        try {
            OrderCreatedEvent event = new OrderCreatedEvent(orderId, userId);
            String message = objectMapper.writeValueAsString(event);

            kafkaTemplate.send("order-created-topic", message);
        } catch (Exception e) {
            log.error("Failed to publish Kafka event", e);
        }
    }

    public void publishUserActivityViewedProductEvent(UserActivityEvent userActivityEvent) {
        try {
            String message = objectMapper.writeValueAsString(userActivityEvent);

            kafkaTemplate.send("user-activity-topic", message);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize UserActivityEvent: ", e);

            throw new RuntimeException("Failed to serialize UserActivityEvent", e);
        }
    }

}
