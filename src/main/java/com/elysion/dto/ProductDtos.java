package com.elysion.dto;

import com.elysion.domain.Product;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Sammlung von DTOs für Produkte:
 * - ProductPublicDTO: Ausgabe für öffentliche Endpunkte (/public/...).
 * - ProductUpsertDTO: Eingabe für Admin-Endpunkte (/admin/...) zum Anlegen/Ändern.
 */
public class ProductDtos {

    /** Öffentliche Darstellung eines Produkts (nur lesende Ausgabe). */
    public static class ProductPublicDTO {
        public UUID id;
        public String sku;
        public String name;
        public String description;
        public Integer priceCents;
        public String currency;
        public OffsetDateTime createdAt;
        public UUID sellerId;

        /** Mappt eine Product-Entity auf das Public-DTO. */
        public static ProductPublicDTO fromEntity(Product p) {
            var d = new ProductPublicDTO();
            d.id = p.id;
            d.sku = p.sku;
            d.name = p.name;
            d.description = p.description;
            d.priceCents = p.priceCents;
            d.currency = p.currency;
            d.createdAt = p.createdAt;
            d.sellerId = (p.seller != null ? p.seller.id : null);
            return d;
        }
    }

    /** Eingabe-DTO für Admin: Erstellen/Ändern eines Produkts. */
    public static class ProductUpsertDTO {
        public String sku;
        public String name;
        public String description;
        public Integer priceCents;
        public String currency;   // optional, Default "EUR" im Service/Resource setzen
        public UUID sellerId;     // nur von ADMIN auswerten; Seller-User ignorieren/überschreiben
    }
}