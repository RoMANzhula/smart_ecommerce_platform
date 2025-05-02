package org.romanzhula.payment_service.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageSenderService {

    private final KafkaTemplate<String, String> kafkaTemplate;


    public void sendSuccessMessage(String orderId) {
        kafkaTemplate.send("payment-success-topic", orderId);
        log.info("Sent success message for orderId={}", orderId);
    }

    public void sendFailureMessage(String orderId) {
        kafkaTemplate.send("payment-failed-topic", orderId);
        log.info("Sent success message for orderId={}", orderId);
    }

}
