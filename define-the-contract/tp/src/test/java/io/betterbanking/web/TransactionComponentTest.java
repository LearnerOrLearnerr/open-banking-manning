package io.betterbanking.web;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import io.betterbanking.BaseTest;

import static io.restassured.RestAssured.given;

@SpringBootTest
public class TransactionComponentTest extends BaseTest {
 
    @Test
    public void testApplicationEndToEnd() {
        final String uri = getBaseUrl() + "/transactions/123";

        // uses rest-assured
        given()
            .when()
            .get(uri)
            .then()
            .statusCode(Matchers.is(200));
    }
}
