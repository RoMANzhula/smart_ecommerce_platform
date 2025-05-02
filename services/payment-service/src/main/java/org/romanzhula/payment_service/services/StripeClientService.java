package org.romanzhula.payment_service.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;


@Service
@Slf4j
@RequiredArgsConstructor
public class StripeClientService {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    private final WebClient stripWebClient;


    public boolean processPayment(String token, double amount) {
        return Boolean.TRUE.equals(stripWebClient.post()
                .uri("/charges")
                .header("Authorization", "Bearer " + stripeSecretKey)
                .bodyValue(Map.of(
                        "amount", (int) (amount * 100), // to cents
                        "currency", "usd",
                        "source", token
                ))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> "succeeded".equals(response.get("status")))
                .block())
        ;
    }

}
