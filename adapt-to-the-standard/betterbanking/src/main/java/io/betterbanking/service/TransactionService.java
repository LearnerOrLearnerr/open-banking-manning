package io.betterbanking.service;

import io.betterbanking.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionApiClient apiClient;

    public List<Transaction> findAllByAccountNumber(final String acctNr) {
        List<Transaction> list = apiClient.getTransactions(acctNr);

        for (Transaction t : list) {
            t.setMerchantLogo(t.getMerchantName());
        }

        return list;
    }
}
