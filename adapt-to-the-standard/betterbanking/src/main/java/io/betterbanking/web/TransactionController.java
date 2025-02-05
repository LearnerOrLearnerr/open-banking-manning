package io.betterbanking.web;

import io.betterbanking.entity.Transaction;
import io.betterbanking.service.TransactionService;
import io.betterbanking.web.dto.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    @Autowired
    TransactionService svc;

    @GetMapping("/{acctNr}")
    public List<TransactionDto> findAllByAccountNumber (@PathVariable final Integer acctNr) {
        List<Transaction> list = svc.findAllByAccountNumber(acctNr);
        List<TransactionDto> listDto = new ArrayList<>(list.size());

        for (Transaction txn : list) {
            TransactionDto dto = buildTransactionDto (txn);
            listDto.add(dto);
        }

        return listDto;
    }

    private TransactionDto buildTransactionDto (Transaction txn) {
        TransactionDto dto = TransactionDto.builder()
                .accountNumber(txn.getAccountNumber())
                .currency(txn.getCurrency())
                .amount(txn.getAmount())
                .build();

        return dto;
    }
}
