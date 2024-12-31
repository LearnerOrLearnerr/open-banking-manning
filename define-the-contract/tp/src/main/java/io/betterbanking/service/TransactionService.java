package io.betterbanking.service;

import io.betterbanking.entity.Transaction;
import io.betterbanking.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository repo;

    @Autowired
    public TransactionService (final TransactionRepository repo) {
        this.repo = repo;
    }

    /**
     * Fetch transactions list by account number
     * @param accountNumber
     * @return
     */
    public List<Transaction> findAllByAccountNumber (final Integer accountNumber) {
        return repo.findAllByAccountNumber(accountNumber);
    }
}
