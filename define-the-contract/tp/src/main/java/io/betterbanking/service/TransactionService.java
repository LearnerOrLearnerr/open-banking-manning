package io.betterbanking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.betterbanking.entity.Transaction;
import io.betterbanking.repository.TransactionRepository;

@Service
public class TransactionService {

    private TransactionRepository repo;

    @Autowired
    public TransactionService (TransactionRepository repo) {
        this.repo = repo;
    }

    /**
     * Fetch transactions list by account number
     * @param accountNumber
     * @return
     */
    public Iterable<Transaction> findAllByAccountNumber (final Integer accountNumber) {
        Iterable<Transaction> txns = repo.findByAccountNumber(accountNumber);
        return txns;
    }
}
