package io.betterbanking.tp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path="/api/account", produces="application/json")
public class TransactionController {

    @Autowired
    private TransactionService svc;

    // http://localhost:8080/api/account/123
    @GetMapping(params="/{id}")
    public List<Transaction> getTransactions(@PathVariable("id") String id) {
        List<Transaction> txnList = svc.findAllByAccountNumber(id);
        return txnList;
    }    
}
