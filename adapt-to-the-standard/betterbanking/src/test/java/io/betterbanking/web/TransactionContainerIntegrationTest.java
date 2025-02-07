package io.betterbanking.web;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;


import static io.restassured.RestAssured.given;

/**
 * Database provided in a separate container using Testcontainers in the base class
 */
@SpringBootTest (webEnvironment = WebEnvironment.RANDOM_PORT)
public class TransactionContainerIntegrationTest extends BaseContainersTest {

    private static final Integer ACCOUNT_NUMBER = 123;

    @LocalServerPort
    private int port;

    @Autowired
    private TransactionController controller;

    @Test
    public void shouldReturnEmptyListWhenFreshDatabase() {
        final String uri = String.format("http://localhost:%d/api/v1/transactions/%d", port, ACCOUNT_NUMBER);

        // Http 200, rest-assured
        given().get(uri)
                .then()
                .statusCode(Matchers.is(200));
    }
}
