package org.romanzhula.order_service.models.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserActivityEvent {

    private String userId;
    private String productId;
    private double score;
    private String action;

}

