package io.betterbanking.web;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;


import static io.restassured.RestAssured.given;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TransactionComponentTest {   

    @LocalServerPort
    private int port;

    @Test
    public void testApplicationEndToEnd() {
        final String uri = String.format ("http://localhost:%s/api/v1/transactions/123", port);
        given()
            .when()
            .get(uri)
            .then()
            .statusCode(Matchers.is(200));
    }
}
