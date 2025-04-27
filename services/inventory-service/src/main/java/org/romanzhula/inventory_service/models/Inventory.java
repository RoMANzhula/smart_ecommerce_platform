package org.romanzhula.inventory_service.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serial;
import java.io.Serializable;


@Data
@RedisHash("inventory")
public class Inventory implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String productId;

    private int quantity;

}
