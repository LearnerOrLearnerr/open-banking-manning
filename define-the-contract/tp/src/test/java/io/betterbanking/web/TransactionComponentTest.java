package io.betterbanking.web;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import io.betterbanking.BaseTest;

import static io.restassured.RestAssured.given;

@SpringBootTest
@Testcontainers
public class TransactionComponentTest extends BaseTest {   
 
    @BeforeAll
    public static void beforeAll () {
        mongo.start();
    }

    @AfterAll
    public static void afterAll() {
        mongo.stop();
    }

    @Test
    public void testApplicationEndToEnd() {
        final String uri = getBaseUrl() + "/transactions/123";

        given()
            .when()
            .get(uri)
            .then()
            .statusCode(Matchers.is(200));
    }
}
