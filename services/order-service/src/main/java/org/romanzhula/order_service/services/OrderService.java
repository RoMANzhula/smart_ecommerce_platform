package org.romanzhula.order_service.services;

import lombok.RequiredArgsConstructor;
import org.romanzhula.order_service.models.Order;
import org.romanzhula.order_service.repositories.OrderRepository;
import org.romanzhula.order_service.requests.UpdateInventoryRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final KafkaProducerService kafkaProducer;
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

            kafkaProducer.publishOrderCreatedEvent(savedOrder.getId().toString());

            return savedOrder;
        } else {
            throw new RuntimeException("Insufficient inventory for product: " + order.getProductId());
        }
    }

}
