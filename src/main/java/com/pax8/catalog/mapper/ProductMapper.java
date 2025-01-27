package com.pax8.catalog.mapper;

import com.pax8.catalog.dto.ProductRequest;
import com.pax8.catalog.dto.ProductResponse;
import com.pax8.catalog.dto.ProductUpdateRequest;
import com.pax8.catalog.entity.Product;

public class ProductMapper {

    private ProductMapper() {}

    public static ProductResponse toProductResponse(Product product) {
        if (product == null) {
            return null;
        }

        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getCreated(),
            product.getUpdated()
        );
    }

    public static Product toProduct(ProductRequest productRequest) {
        if (productRequest == null) {
            return null;
        }

        return Product.builder()
            .name(productRequest.name())
            .description(productRequest.description())
            .price(productRequest.price())
            .build();
    }

    public static Product toProduct(ProductUpdateRequest productRequest, Product existingProduct) {
        if (productRequest == null || existingProduct == null) {
            return null;
        }

        existingProduct.setName(productRequest.name());
        existingProduct.setDescription(productRequest.description());
        existingProduct.setPrice(productRequest.price());

        return existingProduct;
    }
}
