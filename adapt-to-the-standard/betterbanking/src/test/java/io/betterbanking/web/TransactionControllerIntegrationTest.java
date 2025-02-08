package io.betterbanking.web;

import io.betterbanking.service.TransactionService;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.mockito.Mockito.when;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

/**
 * Database provided in a separate container using Testcontainers in the base class
 */
@SpringBootTest (webEnvironment = WebEnvironment.RANDOM_PORT)
public class TransactionControllerIntegrationTest {

    private static final Integer ACCOUNT_NUMBER = 123;

    @LocalServerPort
    private int port;

    @MockitoBean
    private TransactionService svc;

    @Test
    public void shouldReturnEmptyListForNewAccount() {
        final String uri = String.format("http://localhost:%d/api/v1/transactions/%d", port, ACCOUNT_NUMBER);

        when(svc.findAllByAccountNumber(ACCOUNT_NUMBER)).thenReturn(List.of());

        // Http 200, rest-assured
        ValidatableResponse response = given().get(uri).then();
        response.statusCode(Matchers.is(200));
    }
}