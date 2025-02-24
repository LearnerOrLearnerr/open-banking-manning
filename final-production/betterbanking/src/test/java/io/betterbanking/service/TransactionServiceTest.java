package io.betterbanking.service;

import io.betterbanking.entity.Transaction;
import io.betterbanking.repository.TransactionApiClient;
import io.betterbanking.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Unit test of TransactionService
 * Based on the following @Autowired components of TransactionService
 * @MockitoBean TransactionApiClient
 * @MockitoBean TransactionRepository
 */
@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    private TransactionService svc;

    @MockitoBean
    private TransactionApiClient apiClient;

    @MockitoBean
    private TransactionRepository repo;

    @Test
    @WithMockUser(roles="999")
    public void shouldReturnEmptyListWhenAccountNumberNotFound () {
        final Integer ACCOUNT_NUMBER = 999;
        when (apiClient.findAllByAccountNumber(ACCOUNT_NUMBER)).thenReturn(List.of());

        List<Transaction> list = svc.findAllByAccountNumber(ACCOUNT_NUMBER);
        assertTrue (list.isEmpty());
    }

    @Test
    @WithMockUser(username="ragamuffin", roles="123")
    public void shouldReturnTransactionsWhenAccountNumberFound() {
        final Integer ACCOUNT_NUMBER = 123;

        List<Transaction> list = new ArrayList<>();
        list.add (buildTransaction(ACCOUNT_NUMBER, "GBP", 35.0));
        list.add (buildTransaction(ACCOUNT_NUMBER, "GBP", 135.0));

        when(apiClient.findAllByAccountNumber(ACCOUNT_NUMBER)).thenReturn(list);

        List<Transaction> txns = svc.findAllByAccountNumber(ACCOUNT_NUMBER);
        assertFalse (txns.isEmpty());
        assertEquals (ACCOUNT_NUMBER, txns.get(0).getAccountNumber());
    }

     public Transaction buildTransaction(Integer acctNr, String curr, Double amt) {
         Transaction txn = Transaction.builder()
                 .accountNumber(acctNr)
                 .amount(amt)
                 .currency(curr)
                 .date (new Date())
                 .build();

         return txn;
     }
}
