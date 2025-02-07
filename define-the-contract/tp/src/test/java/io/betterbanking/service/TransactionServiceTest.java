package io.betterbanking.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.betterbanking.BaseTest;
import io.betterbanking.entity.Transaction;

@SpringBootTest
public class TransactionServiceTest extends BaseTest {

    @Autowired
    private TransactionService txnService;

    @Test
    public void testService () {
        List<Transaction> list = txnService.findAllByAccountNumber(123);
        long size = list.stream().count();
        assertTrue (size >= 0);
    }
}
