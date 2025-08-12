package com.elysion.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "sellers")
public class Seller extends PanacheEntityBase {

    @Id
    @Column(name = "id", nullable = false)
    public UUID id;

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "contact_email", unique = true)
    public String contactEmail;

    @Column(name = "created_at", nullable = false)
    public OffsetDateTime createdAt;

    @Column(name = "owner_user_id", nullable = false) // nach ChangeSet #5
    public UUID ownerUserId;

    public Seller() {}

    public Seller(UUID id, String name, String contactEmail, OffsetDateTime createdAt, UUID ownerUserId) {
        this.id = id;
        this.name = name;
        this.contactEmail = contactEmail;
        this.createdAt = createdAt;
        this.ownerUserId = ownerUserId;
    }
}