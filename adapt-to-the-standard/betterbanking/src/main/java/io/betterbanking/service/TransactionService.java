package io.betterbanking.service;

import io.betterbanking.entity.Transaction;
import io.betterbanking.repository.TransactionApiClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionApiClient apiClient;

    @CircuitBreaker(name = "transactionService",fallbackMethod = "foundNone")
    public List<Transaction> findAllByAccountNumber(final Integer acctNr) {
        List<Transaction> list = apiClient.findAllByAccountNumber(acctNr);

        for (Transaction t : list) {
            t.setMerchantLogo(t.getMerchantName());
        }

        return list;
    }

    public List<Transaction> foundNone(final Integer acctNr, Throwable t) {
        return List.of();
    }
}
