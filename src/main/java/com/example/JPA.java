package com.example;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;

import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@GraphQLApi
@Path("/jpa")
public class JPA {
    @PersistenceContext SessionFactory sessionFactory;

    @Query
    @GET @Path("/statistics")
    public JpaStatistics statistics() {
        return new JpaStatistics(sessionFactory.getStatistics());
    }

    /** The actual StatisticsImpl has a managementBean field causing a recursive reference */
    @RequiredArgsConstructor
    public static class JpaStatistics implements Statistics {
        @Delegate
        private final Statistics statistics;
    }
}
