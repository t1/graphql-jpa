package com.example;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.microprofile.graphql.Id;
import org.eclipse.microprofile.graphql.NonNull;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Cacheable @Entity
@Getter @Setter @ToString
public class Product {
    @Id @javax.persistence.Id
    @GeneratedValue
    Long id;

    @NonNull @NotNull @Size(min = 1, max = 100)
    String name;

    @NonNull @NotNull @Size(min = 1, max = 4096)
    String description;
}
