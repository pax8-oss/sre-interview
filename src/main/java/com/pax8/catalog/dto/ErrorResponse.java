package com.pax8.catalog.dto;

import java.time.Instant;
import java.util.Map;

public record ErrorResponse(
    Map<String, String> errors,
    String code,
    String timestamp
) {
    public ErrorResponse(Map<String, String> errors, String code) {
        this(errors, code, Instant.now().toString());
    }
}
