package io.betterbanking.web;

import io.betterbanking.entity.Transaction;
import io.betterbanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    @Autowired
    TransactionService svc;

    @GetMapping("/{acctNr}")
    public List<Transaction> findAllByAccountNumber (@PathVariable final Integer acctNr) {
        return svc.findAllByAccountNumber(acctNr);
    }
}
