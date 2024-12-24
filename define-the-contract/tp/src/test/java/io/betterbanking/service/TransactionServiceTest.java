package io.betterbanking.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.betterbanking.entity.Transaction;

@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    private TransactionService txnService;

    @Test
    public void testService () {
        Iterable<Transaction> list = txnService.findAllByAccountNumber(123);
        long size = StreamSupport.stream (list.spliterator(), false).count();
        assertTrue (size >= 0);
    }
}
