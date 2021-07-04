package com.example;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ProductsTest {

    @Test
    public void testHelloEndpoint() {
        given().body("{\"query\":\"{hello}\"}\n")
            .when().post("/graphql")
            .then().statusCode(200)
            .body(is("{\"data\":{\"hello\":\"Hello, GraphQL!\"}}"));
    }

    @Test
    public void testGetProduct() {
        given().body("{\"query\":\"{product(id:1) {id name description}}\"}\n")
            .when().post("/graphql")
            .then().statusCode(200)
            .body(is("{\"data\":{\"product\":{\"id\":\"1\",\"name\":\"Chair\",\"description\":\"some chair\"}}}"));
    }
}
