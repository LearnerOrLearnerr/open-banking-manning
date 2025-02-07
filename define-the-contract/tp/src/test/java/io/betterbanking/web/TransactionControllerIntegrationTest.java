package io.betterbanking.web;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import io.betterbanking.BaseTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerIntegrationTest extends BaseTest {

    @Autowired
    MockMvc mvc;

    @Test
    public void testTransactionController() throws Exception {
        
        final String uri = getBaseUrl() + "/transactions/123";

        // uses Spring Boot Test's MockMvc
        mvc.perform (get (uri))
            .andExpect(status().isOk());
    }
}
