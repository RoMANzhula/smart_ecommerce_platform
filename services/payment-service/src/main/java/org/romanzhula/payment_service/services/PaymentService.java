package org.romanzhula.payment_service.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.romanzhula.payment_service.models.Payment;
import org.romanzhula.payment_service.repositories.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final StripeClientService stripeClient;
    private final KafkaMessageSenderService kafkaMessageSender;


    @Transactional
    public Payment processPayment(String orderId, String token, double amount) {
        log.info("Attempting to process payment for orderId={} amount={}", orderId, amount);

        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setAmount(amount);

        boolean success = stripeClient.processPayment(token, amount);

        if (success) {
            payment.setPaymentStatus("SUCCESS");
            kafkaMessageSender.sendSuccessMessage(orderId);
        } else {
            payment.setPaymentStatus("FAILED");
            kafkaMessageSender.sendFailureMessage(orderId);
        }

        return paymentRepository.save(payment);
    }

}
