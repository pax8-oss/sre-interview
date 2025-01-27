package com.pax8.catalog.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.pax8.catalog.repository.ProductRepository;

@Component
public class UniqueProductNameValidator implements ConstraintValidator<UniqueProductName, String> {

    private final ProductRepository productRepository;

    @Autowired
    public UniqueProductNameValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean isValid(String productName, ConstraintValidatorContext context) {
        if (productName == null || productName.isBlank()) {
            return true;
        }

        return !productRepository.existsByName(productName);
    }
}
