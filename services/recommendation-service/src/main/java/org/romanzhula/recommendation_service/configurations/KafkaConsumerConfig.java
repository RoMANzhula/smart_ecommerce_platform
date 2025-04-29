package org.romanzhula.recommendation_service.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.romanzhula.recommendation_service.models.Recommendation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Recommendation> kafkaListenerContainerFactory(
            ConsumerFactory<String, Recommendation> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, Recommendation> factory =
                new ConcurrentKafkaListenerContainerFactory<>()
        ;

        factory.setConsumerFactory(consumerFactory);

        return factory;
    }

    @Bean
    public ConsumerFactory<String, Recommendation> consumerFactory(ObjectMapper objectMapper) {
        JsonDeserializer<Recommendation> deserializer =
                new JsonDeserializer<>(Recommendation.class, objectMapper, false)
        ;
        deserializer.addTrustedPackages("*");

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "recommendation-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

}
