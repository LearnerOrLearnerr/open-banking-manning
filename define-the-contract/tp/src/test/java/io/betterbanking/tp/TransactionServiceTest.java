package io.betterbanking.tp;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    private TransactionService txnService;

    @Test
    public void testService () {
        List<Transaction> list = txnService.findAllByAccountNumber("123");
        assertTrue (list.size() == 3 || list.size() == 5);
    }
}
