package org.romanzhula.notification_service.services;

import lombok.RequiredArgsConstructor;
import org.romanzhula.notification_service.dto.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final NotificationService notificationService;
    private final WebClient userServiceWebClient;

    
    @KafkaListener(topics = "order-created-topic", groupId = "notification-group")
    public void handleUserCreatedOrder(OrderCreatedEvent event) {
        String email = getEmailByUserId(event.getUserId());
        String orderId = event.getOrderId();

        notificationService.sendByMail(
                email,
                "Your order has been created",
                "Thank you for your order №" + orderId + "! Have a good day!"
        );
    }

    private String getEmailByUserId(String userId) {
        return userServiceWebClient
                .get()
                .uri("/api/v1/users/{userId}/email", userId)
                .retrieve()
                .bodyToMono(String.class)
                .block()
        ;
    }

}
