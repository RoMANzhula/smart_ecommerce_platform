package org.romanzhula.inventory_service.services;

import lombok.RequiredArgsConstructor;
import org.romanzhula.inventory_service.models.Inventory;
import org.romanzhula.inventory_service.repositories.InventoryRepository;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;


    public void updateInventory(String productId, int quantity) {
        Inventory inventory = inventoryRepository.findById(productId).orElse(new Inventory());

        inventory.setProductId(productId);
        inventory.setQuantity(inventory.getQuantity() + quantity);

        inventoryRepository.save(inventory);
    }

    public boolean isAvailable(String productId, int quantity) {
        return inventoryRepository.findById(productId)
                .map(inv -> inv.getQuantity() >= quantity)
                .orElse(false)
        ;
    }

}
