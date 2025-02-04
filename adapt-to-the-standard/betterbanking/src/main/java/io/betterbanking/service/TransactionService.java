package io.betterbanking.service;

import io.betterbanking.entity.Transaction;
import io.betterbanking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository repo;

    public List<Transaction> findAllByAccount (final Integer acctNr) {
        return repo.findAllByAccount (acctNr);
    }
}
