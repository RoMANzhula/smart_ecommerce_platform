package org.romanzhula.inventory_service.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Data
@RedisHash("inventory")
public class Inventory {

    @Id
    private String productId;

    private int quantity;

}
