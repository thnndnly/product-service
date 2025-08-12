package com.elysion.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "products")
public class Product extends PanacheEntityBase {

    @Id
    @Column(name = "id", nullable = false)
    public UUID id;

    @Column(name = "sku", nullable = false, unique = true)
    public String sku;

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "description")
    public String description;

    @Column(name = "price_cents", nullable = false)
    public Integer priceCents;

    @Column(name = "currency", nullable = false, length = 3)
    public String currency = "EUR";

    @Column(name = "created_at", nullable = false)
    public OffsetDateTime createdAt;

    // Convenience constructor
    public Product() {
    }

    public Product(UUID id, String sku, String name, String description, Integer priceCents, String currency, OffsetDateTime createdAt) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.priceCents = priceCents;
        this.currency = currency;
        this.createdAt = createdAt;
    }
}