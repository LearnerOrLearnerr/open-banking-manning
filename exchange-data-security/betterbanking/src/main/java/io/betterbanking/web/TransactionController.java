package io.betterbanking.web;

import io.betterbanking.entity.Transaction;
import io.betterbanking.service.TransactionService;
import io.betterbanking.web.dto.TransactionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {

    Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    TransactionService svc;

    @GetMapping("/transactions/{acctNr}")
    public List<TransactionDto> findAllByAccountNumber (@PathVariable final Integer acctNr) {

        logger.info ("Fetching transactions for account {}", acctNr);

        List<Transaction> list = svc.findAllByAccountNumber(acctNr);

        List<TransactionDto> listDto = list.stream()
                .map (this::buildTransactionDto)
                .collect(Collectors.toList());

        logger.info ("Fetched {} transactions for account {}", listDto.size(), acctNr);

        return listDto;
    }

    @GetMapping("/accounts")
    public List<Integer> findAllAccountNumber() {
        List<Integer> listAccounts = svc.findDistinctAccountNumbers();
        logger.info ("{} accounts found", listAccounts.size());

        return listAccounts;
    }

    @PostMapping("/poll/{acctNr}")
    public String syncTransactions (@PathVariable Integer acctNr) {
        List<Transaction> list = svc.pollByAccountNumber(acctNr);
        String response = String.format( "{ status: \"%d transactions added to local repo\"}", list.size());
        logger.info(response);
        return response;
    }

    private TransactionDto buildTransactionDto (Transaction txn) {
        TransactionDto dto = TransactionDto.builder()
                .accountNumber(txn.getAccountNumber())
                .currency(txn.getCurrency())
                .amount(txn.getAmount())
                .date (txn.getDate())
                .type (txn.getType())
                .id (txn.getId())
                .build();

        return dto;
    }
}
