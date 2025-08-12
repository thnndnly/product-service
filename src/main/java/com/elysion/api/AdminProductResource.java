// src/main/java/com/elysion/api/AdminProductResource.java
package com.elysion.api;

import com.elysion.domain.Product;
import com.elysion.domain.Seller;
import com.elysion.dto.ProductDtos.ProductPublicDTO;
import com.elysion.dto.ProductDtos.ProductUpsertDTO;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.OffsetDateTime;
import java.util.UUID;

@Path("/admin/products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed({"ADMIN"})
public class AdminProductResource {

    @POST @Transactional
    public Response create(ProductUpsertDTO dto) {
        if (dto.sellerId == null) throw new BadRequestException("sellerId required");
        Seller s = Seller.findById(dto.sellerId);
        if (s == null) throw new BadRequestException("sellerId invalid");

        var p = new Product();
        p.sku = dto.sku; p.name = dto.name; p.description = dto.description;
        p.priceCents = dto.priceCents; p.currency = (dto.currency != null? dto.currency : "EUR");
        p.createdAt = OffsetDateTime.now();
        p.seller = s;
        p.persist();

        return Response.status(Response.Status.CREATED).entity(ProductPublicDTO.fromEntity(p)).build();
    }

    @PUT @Path("/{id}") @Transactional
    public Response update(@PathParam("id") UUID id, ProductUpsertDTO dto) {
        Product p = Product.findById(id);
        if (p == null) throw new NotFoundException();

        if (dto.sellerId != null) {
            Seller s = Seller.findById(dto.sellerId);
            if (s == null) throw new BadRequestException("sellerId invalid");
            p.seller = s;
        }
        if (dto.sku != null) p.sku = dto.sku;
        if (dto.name != null) p.name = dto.name;
        if (dto.description != null) p.description = dto.description;
        if (dto.priceCents != null) p.priceCents = dto.priceCents;
        if (dto.currency != null) p.currency = dto.currency;

        return Response.ok(ProductPublicDTO.fromEntity(p)).build();
    }

    @DELETE @Path("/{id}") @Transactional
    public Response delete(@PathParam("id") UUID id) {
        Product p = Product.findById(id);
        if (p == null) throw new NotFoundException();
        p.delete();
        return Response.noContent().build();
    }
}