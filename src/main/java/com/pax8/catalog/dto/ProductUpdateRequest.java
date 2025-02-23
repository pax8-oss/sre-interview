package com.pax8.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductUpdateRequest(
    @NotBlank
    String name,

    @NotBlank
    String description,

    @NotNull
    BigDecimal price
) {
}
