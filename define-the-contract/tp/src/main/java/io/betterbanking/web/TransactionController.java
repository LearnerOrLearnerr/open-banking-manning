package io.betterbanking.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.betterbanking.entity.Transaction;
import io.betterbanking.service.TransactionService;


@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    @Autowired
    private TransactionService svc;

    @GetMapping("/{accountNumber}")
    public Iterable<Transaction> getTransactions(@PathVariable("accountNumber") final Integer accountNumber) {
        Iterable<Transaction> txnList = svc.findAllByAccountNumber(accountNumber);
        return txnList;
    }    
}
