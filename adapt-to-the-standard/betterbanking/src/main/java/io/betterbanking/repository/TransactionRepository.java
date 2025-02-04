package io.betterbanking.repository;

import io.betterbanking.entity.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionRepository {
    public List<Transaction> findAllByAccount(final Integer acctNr) {
        Transaction txn = Transaction.builder()
                .currency("GBP")
                .amount(5.0)
                .accountNumber(acctNr)
                .build();

        Transaction txn2 = Transaction.builder()
                .currency("GBP")
                .amount(10.0)
                .accountNumber(acctNr)
                .build();

        List<Transaction> list = List.of (txn, txn2);
        return list;
    }
}
