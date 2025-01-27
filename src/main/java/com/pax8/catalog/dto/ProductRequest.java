package com.pax8.catalog.dto;

import com.pax8.catalog.validation.UniqueProductName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequest(
    @NotBlank
    @UniqueProductName
    String name,

    @NotBlank
    String description,

    @NotNull
    BigDecimal price
) {
}
