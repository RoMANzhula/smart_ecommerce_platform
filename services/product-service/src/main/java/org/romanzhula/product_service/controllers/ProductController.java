package org.romanzhula.product_service.controllers;

import lombok.RequiredArgsConstructor;
import org.romanzhula.product_service.models.Product;
import org.romanzhula.product_service.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(
            @RequestBody Product product
    ) {
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

}
