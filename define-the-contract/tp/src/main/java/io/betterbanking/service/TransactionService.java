package io.betterbanking.service;

import java.util.List;
import java.util.Date;
import java.util.LinkedList;
import org.springframework.stereotype.Service;

import io.betterbanking.entity.Transaction;

@Service
public class TransactionService {

    /**
     * Fetch transactions list by account number
     * @param accountNumber
     * @return
     */
    public List<Transaction> findAllByAccountNumber (final Integer accountNumber) {
        List<Transaction> list = new LinkedList<>();

        for (int i=0; i<5; i++) {
            Transaction txn = new Transaction();
            txn.setAccountNumber(accountNumber);
            txn.setCurrency("GBP");
            txn.setMerchantName(i%2 == 0 ? "Asda" : "Tesco");

            txn.setAmount(Double.valueOf(10*i));
            txn.setDate(new Date());

            list.add (txn);
        }

        return list;
    }
}
