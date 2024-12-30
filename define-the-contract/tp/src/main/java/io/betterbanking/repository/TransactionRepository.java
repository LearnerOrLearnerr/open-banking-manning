package io.betterbanking.repository;

import io.betterbanking.entity.Transaction;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionRepository extends MongoRepository <Transaction, String> {
    public Iterable<Transaction> findByAccountNumber (Integer accountNumber);
}
