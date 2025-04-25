package org.romanzhula.order_service.services;

import lombok.RequiredArgsConstructor;
import org.romanzhula.order_service.models.Order;
import org.romanzhula.order_service.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final KafkaProducerService kafkaProducer;


    @Transactional
    public Order createOrder(Order order) {
        Order savedOrder = orderRepository.save(order);

        kafkaProducer.publishOrderCreatedEvent(savedOrder.getId().toString());

        return savedOrder;
    }

}
