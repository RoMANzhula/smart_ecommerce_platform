package org.romanzhula.inventory_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final InventoryService inventoryService;

    @KafkaListener(topics = "product-created-topic", groupId = "inventory-group")
    public void handleProductCreated(String productId) {
        inventoryService.updateInventory(productId, 0);
    }

}
