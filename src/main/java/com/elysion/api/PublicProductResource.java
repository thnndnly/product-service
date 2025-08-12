// src/main/java/com/elysion/api/PublicProductResource.java
package com.elysion.api;

import com.elysion.domain.Product;
import com.elysion.dto.ProductDtos.ProductPublicDTO;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("/public/products")
@Produces(MediaType.APPLICATION_JSON)
public class PublicProductResource {

    @GET
    public Response list(@QueryParam("q") String q,
                         @QueryParam("sellerId") UUID sellerId,
                         @QueryParam("page") @DefaultValue("0") int page,
                         @QueryParam("size") @DefaultValue("20") int size) {

        StringBuilder jpql = new StringBuilder("1=1");
        Parameters params = new Parameters();

        if (q != null && !q.isBlank()) {
            jpql.append(" and (lower(name) like :q or lower(description) like :q or lower(sku) like :q)");
            params.and("q", "%" + q.toLowerCase() + "%");
        }
        if (sellerId != null) { jpql.append(" and seller.id = :sid"); params.and("sid", sellerId); }

        List<Product> products = Product.find(jpql.toString(), params)
                .page(Page.of(page, size)).list();

        var out = products.stream().map(ProductPublicDTO::fromEntity).toList();
        return Response.ok(out).build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") UUID id) {
        Product p = Product.findById(id);
        if (p == null) throw new NotFoundException();
        return Response.ok(ProductPublicDTO.fromEntity(p)).build();
    }
}