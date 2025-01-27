package com.pax8.catalog.controller;

import com.pax8.catalog.dto.ProductRequest;
import com.pax8.catalog.dto.ProductResponse;
import com.pax8.catalog.dto.ProductUpdateRequest;
import com.pax8.catalog.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getProducts(Pageable pageable) {
        Page<ProductResponse> products = productService.getProducts(pageable);

        return ResponseEntity
            .ok()
            .body(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable UUID id) {
        ProductResponse product = productService.getProduct(id);

        return ResponseEntity
            .ok()
            .body(product);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        ProductResponse product = productService.createProduct(productRequest);

        return ResponseEntity
            .ok()
            .body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable UUID id, @Valid @RequestBody ProductUpdateRequest productRequest) {
        ProductResponse product = productService.updateProduct(id, productRequest);

        return ResponseEntity
            .ok()
            .body(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);

        return ResponseEntity
            .noContent()
            .build();
    }
}
