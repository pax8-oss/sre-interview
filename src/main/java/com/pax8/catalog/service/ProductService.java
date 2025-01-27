package com.pax8.catalog.service;

import com.pax8.catalog.dto.ProductRequest;
import com.pax8.catalog.dto.ProductResponse;
import com.pax8.catalog.dto.ProductUpdateRequest;
import com.pax8.catalog.entity.Product;
import com.pax8.catalog.exception.ResourceNotFoundException;
import com.pax8.catalog.mapper.ProductMapper;
import com.pax8.catalog.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<ProductResponse> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
            .map(ProductMapper::toProductResponse);
    }


    public ProductResponse getProduct(UUID id) throws ResourceNotFoundException {
        Product product = getProductById(id);
        return ProductMapper.toProductResponse(product);
    }


    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = ProductMapper.toProduct(productRequest);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toProductResponse(savedProduct);
    }


    public ProductResponse updateProduct(UUID id, ProductUpdateRequest productRequest) throws ResourceNotFoundException {
        Product product = getProductById(id);
        Product updatedProduct = ProductMapper.toProduct(productRequest, product);
        Product savedProduct = productRepository.save(updatedProduct);
        return ProductMapper.toProductResponse(savedProduct);
    }


    public void deleteProduct(UUID id) throws ResourceNotFoundException {
        Product product = getProductById(id);
        productRepository.delete(product);
    }

    private Product getProductById(UUID id) throws ResourceNotFoundException {
        return productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

}
