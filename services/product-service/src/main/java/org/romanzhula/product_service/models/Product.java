package org.romanzhula.product_service.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products")
public class Product implements Serializable {

    @Id
    private String id;

    private String name;

    private double price;

    private String category;

}
