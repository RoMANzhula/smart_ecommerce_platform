package org.romanzhula.inventory_service.controllers;

import lombok.RequiredArgsConstructor;
import org.romanzhula.inventory_service.controllers.requests.UpdateInventoryRequest;
import org.romanzhula.inventory_service.services.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final InventoryService inventoryService;


    @PostMapping("/update")
    public ResponseEntity<Void> updateInventory(
            @RequestBody UpdateInventoryRequest request
    ) {
        inventoryService.updateInventory(request.getProductId(), request.getQuantity());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkAvailability(
            @RequestParam String productId,
            @RequestParam int quantity
    ) {
        return ResponseEntity.ok(inventoryService.isAvailable(productId, quantity));
    }

    @GetMapping("/quantity")
    public ResponseEntity<Integer> getQuantity(
            @RequestParam String productId
    ) {
        return ResponseEntity.ok(inventoryService.getQuantity(productId));
    }

}
