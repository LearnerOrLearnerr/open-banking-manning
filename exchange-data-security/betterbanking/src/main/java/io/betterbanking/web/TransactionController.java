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
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    TransactionService svc;

    @GetMapping("/{acctNr}")
    public List<TransactionDto> findAllByAccountNumber (@PathVariable final Integer acctNr) {

        logger.info ("Fetching transactions for account {}", acctNr);

        List<Transaction> list = svc.findAllByAccountNumber(acctNr);

        List<TransactionDto> listDto = list.stream()
                .map (this::buildTransactionDto)
                .collect(Collectors.toList());

        logger.info ("Fetched transactions for account {}", acctNr);

        return listDto;
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
