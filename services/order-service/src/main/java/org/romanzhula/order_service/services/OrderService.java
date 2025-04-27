package org.romanzhula.order_service.services;

import lombok.RequiredArgsConstructor;
import org.romanzhula.order_service.models.Order;
import org.romanzhula.order_service.repositories.OrderRepository;
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
        Boolean isAvailable = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/check")
                        .queryParam("productId", order.getProductId())
                        .queryParam("quantity", order.getQuantity())
                        .build()
                )
                .retrieve()
                .bodyToMono(Boolean.class)
                .block()
        ;

        if (Boolean.TRUE.equals(isAvailable)) {
            webClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/update")
                            .queryParam("productId", order.getProductId())
                            .queryParam("quantityChange", -order.getQuantity())
                            .build()
                    )
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
