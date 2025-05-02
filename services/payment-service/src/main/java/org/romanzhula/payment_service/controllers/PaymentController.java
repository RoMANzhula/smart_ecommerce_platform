package org.romanzhula.payment_service.controllers;

import lombok.RequiredArgsConstructor;
import org.romanzhula.payment_service.models.Payment;
import org.romanzhula.payment_service.requests.PaymentRequest;
import org.romanzhula.payment_service.services.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;


    @PostMapping("/process")
    public ResponseEntity<Payment> processPayment(
            @RequestBody PaymentRequest request
    ) {
        return ResponseEntity.ok(
                paymentService.processPayment(
                        request.getOrderId(),
                        request.getToken(),
                        request.getAmount()
                )
        );
    }

}
