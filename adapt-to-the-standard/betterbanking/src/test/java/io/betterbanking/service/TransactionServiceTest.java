package io.betterbanking.service;

import io.betterbanking.entity.Transaction;
import io.betterbanking.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Based on @MockitoBean TransactionApiClient, test TransactionService
 */
@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    private TransactionService svc;

    @MockitoBean
    private TransactionApiClient apiClient;

    @Test
    public void shouldReturnEmptyListWhenAccountNumberNotFound () {
        final String ACCOUNT_NUMBER = "999";
        when (apiClient.getTransactions(ACCOUNT_NUMBER)).thenReturn(List.of());

        List<Transaction> list = svc.findAllByAccountNumber(ACCOUNT_NUMBER);
        assertTrue (list.isEmpty());
    }

    @Test
    public void shouldReturnTransactionsWhenAccountNumberFound() {
        final String ACCOUNT_NUMBER = "123";

        List<Transaction> list = new ArrayList<>();
        list.add (buildTransaction(ACCOUNT_NUMBER, "GBP", 35.0));
        list.add (buildTransaction(ACCOUNT_NUMBER, "GBP", 135.0));

        when(apiClient.getTransactions(ACCOUNT_NUMBER)).thenReturn(list);

        List<Transaction> txns = svc.findAllByAccountNumber(ACCOUNT_NUMBER);
        assertFalse (txns.isEmpty());
        assertEquals (ACCOUNT_NUMBER, txns.get(0).getAccountNumber().toString());
    }

     public Transaction buildTransaction(String acctNr, String curr, Double amt) {
         Transaction txn = Transaction.builder()
                 .accountNumber(Integer.valueOf(acctNr))
                 .amount(amt)
                 .currency(curr)
                 .date (new Date())
                 .build();

         return txn;
     }
}
