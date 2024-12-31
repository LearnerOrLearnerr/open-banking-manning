package io.betterbanking.web;

import java.util.List;
import static java.util.stream.Collectors.toList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.betterbanking.entity.Transaction;
import io.betterbanking.service.TransactionService;
import io.betterbanking.web.dto.TransactionDto;


@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService svc;

    @Autowired
    public TransactionController (final TransactionService service) {
        svc = service;
    }

    @GetMapping("/{accountNumber}")
    public List<TransactionDto> getTransactions(@PathVariable("accountNumber") final Integer accountNumber) {
        List<Transaction> txnList = svc.findAllByAccountNumber(accountNumber);
        return txnList.stream()
                .map(x -> TransactionDto.apply(x))
                .collect(toList());
    }    
}
