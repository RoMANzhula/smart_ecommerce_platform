package org.romanzhula.inventory_service.controllers.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInventoryRequest {

    private String productId;
    private int quantity;

}

