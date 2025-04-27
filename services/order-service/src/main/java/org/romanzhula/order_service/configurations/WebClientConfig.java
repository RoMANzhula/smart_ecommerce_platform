package org.romanzhula.order_service.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class WebClientConfig {

    @Bean
    public WebClient inventoryWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl("http://inventory-service/api/v1/inventory")
                .build()
        ;
    }

}
