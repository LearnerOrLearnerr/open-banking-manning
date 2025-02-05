package io.betterbanking.web;

import io.betterbanking.BetterBakingApplication;
import io.restassured.internal.ValidatableResponseImpl;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.TypeReference;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

import org.hamcrest.Matchers;
import org.springframework.boot.test.web.server.LocalServerPort;

/**
 * WebEnvironment required for fully integrated test
 */
@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Test
    public void shouldReturnHttpOk() throws InterruptedException {
        String uri = String.format("http://localhost:%d/api/v1/transactions/123", port);

        // Http 200
        ValidatableResponse resp = given().when()
                .get(uri)
                .then().statusCode(Matchers.is (200));
    }
}
