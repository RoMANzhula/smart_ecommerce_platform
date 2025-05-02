package org.romanzhula.payment_service.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class WebClientConfig {

    @Bean
    public WebClient stripWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.stripe.com/v1")
                .build()
        ;
    }

}
