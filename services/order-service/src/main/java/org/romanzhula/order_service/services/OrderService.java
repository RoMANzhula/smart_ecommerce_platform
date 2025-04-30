package org.romanzhula.order_service.services;

import lombok.RequiredArgsConstructor;
import org.romanzhula.order_service.models.Order;
import org.romanzhula.order_service.models.events.UserActivityEvent;
import org.romanzhula.order_service.repositories.OrderRepository;
import org.romanzhula.order_service.requests.UpdateInventoryRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final KafkaProducerService kafkaProducerService;
    private final WebClient webClient;


    @Transactional
    public Order createOrder(Order order) {
        String inventoryServiceUrl = "http://localhost:8084/api/v1/inventory";

        Boolean isAvailable = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/check")
                        .queryParam("productId", order.getProductId())
                        .queryParam("quantity", order.getQuantity())
                        .build()
                )
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        if (Boolean.TRUE.equals(isAvailable)) {

            UpdateInventoryRequest request = new UpdateInventoryRequest(order.getProductId(), -order.getQuantity());

            webClient.post()
                    .uri("/update")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block()
            ;

            Order savedOrder = orderRepository.save(order);

            kafkaProducerService.publishOrderCreatedEvent(savedOrder.getId().toString(), savedOrder.getUserId().toString());

            UserActivityEvent userActivityEvent = new UserActivityEvent();
            userActivityEvent.setUserId(order.getUserId());
            userActivityEvent.setProductId(order.getProductId());
            userActivityEvent.setScore(0.5); // 0.5 - for viewed product
            userActivityEvent.setAction("VIEWED_PRODUCT");

            kafkaProducerService.publishUserActivityViewedProductEvent(userActivityEvent);

            return savedOrder;
        } else {
            throw new RuntimeException("Insufficient inventory for product: " + order.getProductId());
        }
    }

}
