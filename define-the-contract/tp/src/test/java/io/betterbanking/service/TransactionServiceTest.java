/*package io.betterbanking.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import io.betterbanking.BaseTest;
import io.betterbanking.entity.Transaction;

@SpringBootTest
@Testcontainers
public class TransactionServiceTest extends BaseTest {

    @Autowired
    private TransactionService txnService;

    @BeforeAll
    public static void beforeAll () {
        mongo.start();
    }

    @AfterAll
    public static void afterAll() {
        mongo.stop();
    }

    @Test
    public void testService () {
        List<Transaction> list = txnService.findAllByAccountNumber(123);
        long size = list.stream().count();
        assertTrue (size >= 0);
    }
}
*/