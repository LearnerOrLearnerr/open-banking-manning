package io.betterbanking.web;

import io.betterbanking.service.TransactionService;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static io.restassured.RestAssured.given;

@SpringBootTest (webEnvironment = WebEnvironment.RANDOM_PORT)
public class TransactionControllerIntegrationTest {

    private Logger logger = LoggerFactory.getLogger(TransactionControllerIntegrationTest.class);

    private static final Integer ACCOUNT_NUMBER = 124;

    @LocalServerPort
    private int port;

    private TransactionService svc;

    @Value("${application.better-banking.hostname}")
    private String resourceServerHostName;

    private String accessToken;

    /**
     * Get access token for the resource server
     * The test engine acts as a client application invoking better-banking API's; hence, token is required
     */
    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost:9001/realms/open-banking-realm/protocol";

        Response resourceServerToken = given()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED.toString())
                .formParam("grant_type", "password")
                .formParam("username", "ragamuffin")
                .formParam("password", "ragamuffin")
                .formParam("client_id", "open-banking")
                .formParam("client_secret", "BxfDdnv47dGz3p9kzRenMx5NmqcYkfNi")
                .when()
                .post("/openid-connect/token");

        assertEquals(200, resourceServerToken.getStatusCode());

        accessToken = resourceServerToken.path("access_token");
        assertNotNull(accessToken);
        logger.debug("Got access token: {}", accessToken);
    }

    @Test
    public void shouldReturnTransactionListForValidToken() {
        String betterBankingBaseUri = String.format ("http://%s:%d", resourceServerHostName, port);
        logger.info("Base URI: {}", betterBankingBaseUri);

        final String uri = String.format("%s/api/v1/transactions/%d", betterBankingBaseUri, ACCOUNT_NUMBER);
        logger.info ("URI to fetch transactions: {}", uri);

        // Http 200, rest-assured
        ValidatableResponse response = given()
                .auth().oauth2(accessToken)
                .get(uri)
                .then();
        logger.info("Status code: {}", response.extract().statusCode());
        response.statusCode(Matchers.is(200));
    }
}