package org.romanzhula.product_service.services;

import lombok.RequiredArgsConstructor;
import org.romanzhula.product_service.exceptions.EntityNotFoundException;
import org.romanzhula.product_service.models.Product;
import org.romanzhula.product_service.repositories.ProductRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final KafkaProducerService kafkaProducerService;

    public Product createProduct(Product product) {
        Product savedProduct = productRepository.save(product);

        kafkaProducerService.publishProductCreatedEvent(savedProduct.getId());

        return savedProduct;
    }

    @Cacheable(value = "products", key = "#id")
    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found"))
        ;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

}
