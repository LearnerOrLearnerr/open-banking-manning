package io.betterbanking.repository;

import io.betterbanking.entity.Transaction;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository <Transaction, String> {
    public List<Transaction> findAllByAccountNumber (final Integer accountNumber);
}
