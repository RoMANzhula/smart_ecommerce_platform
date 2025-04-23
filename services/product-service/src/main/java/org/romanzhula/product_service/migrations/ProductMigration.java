package org.romanzhula.product_service.migrations;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.romanzhula.product_service.models.Product;


@RequiredArgsConstructor
@Slf4j
@ChangeUnit(id = "init-products", order = "001", author = "roman")
public class ProductMigration {

    private final MongoTemplate mongoTemplate;

    @Execution
    public void changeSet() {
        log.info(">>> migration starts");
        mongoTemplate.save(new Product(null, "Laptop", 1200.0, "Electronics"));
        mongoTemplate.save(new Product(null, "Phone", 800.0, "Electronics"));
        mongoTemplate.save(new Product(null, "Desk", 200.0, "Furniture"));
    }

    @RollbackExecution
    public void rollback() {
        log.error("Migration Rollback.");
        mongoTemplate.remove(Product.class).all();
    }

}
