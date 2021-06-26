package com.example;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.List;

import static java.lang.Boolean.TRUE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@GraphQLApi
@Path("/products")
public class Products {
    @Inject EntityManager em;

    @Query
    @GET @Path("/hello")
    public String hello() {
        return "Hello, GraphQL!";
    }

    @Query
    @GET @Path("/{id}") @Produces(APPLICATION_JSON)
    public Product product(@PathParam("id") Long id) {
        return em.find(Product.class, id);
    }

    @Query
    @GET @Produces(APPLICATION_JSON)
    public List<Product> products(
        @Valid @Min(0) @DefaultValue("0")
        @QueryParam("offset") @javax.ws.rs.DefaultValue("0")
            int offset,
        @Valid @Min(0) @Max(100) @DefaultValue("100")
        @QueryParam("count") @javax.ws.rs.DefaultValue("100")
            int count
    ) {
        return em.createQuery("SELECT product FROM Product product", Product.class)
            .setHint("org.hibernate.cacheable", TRUE)
            .setFirstResult(offset).setMaxResults(count)
            .getResultList();
    }

    @Transactional @Mutation
    public Product createProduct(@Valid Product product) {
        em.persist(product);
        return product;
    }

    @Transactional @Mutation
    public Product deleteProduct(Long id) {
        var product = em.getReference(Product.class, id);
        em.remove(product);
        return product;
    }
}
