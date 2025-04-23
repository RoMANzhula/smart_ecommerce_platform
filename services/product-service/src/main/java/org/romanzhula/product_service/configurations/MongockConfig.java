package org.romanzhula.product_service.configurations;

import io.mongock.driver.mongodb.springdata.v4.SpringDataMongoV4Driver;
import io.mongock.runner.springboot.MongockSpringboot;
import io.mongock.runner.springboot.base.MongockApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongockConfig {

    @Bean
    public MongockApplicationRunner mongockApplicationRunner(
            ApplicationContext springContext,
            MongoTemplate mongoTemplate) {

        return MongockSpringboot.builder()
                .setDriver(SpringDataMongoV4Driver.withDefaultLock(mongoTemplate))
                .addChangeLogsScanPackage("org.romanzhula.product_service.migrations")
                .setSpringContext(springContext)
                .buildApplicationRunner()
        ;
    }

}
