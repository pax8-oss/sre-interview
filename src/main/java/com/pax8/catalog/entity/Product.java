package com.pax8.catalog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "products", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "created", nullable = false, updatable = false)
    private Instant created;

    @Column(name = "updated", nullable = false)
    private Instant updated;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        this.created = now;
        this.updated = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated = Instant.now();
    }
}
