package org.romanzhula.order_service.controllers;

import lombok.RequiredArgsConstructor;
import org.romanzhula.order_service.models.Order;
import org.romanzhula.order_service.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(
            @RequestBody Order order
    ) {
        return ResponseEntity.ok(orderService.createOrder(order));
    }

}
