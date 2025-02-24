package io.betterbanking.service;

import io.betterbanking.entity.Transaction;
import io.betterbanking.repository.TransactionRepository;
import org.apache.logging.slf4j.SLF4JLogBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TransactionDataLoader implements ApplicationRunner {

    @Autowired
    private TransactionRepository repo;

    private static final Logger logger = LoggerFactory.getLogger(TransactionDataLoader.class);

    public Transaction buildTransaction (String currency, Double amount, Integer accountNumber) {
        final Transaction t = Transaction.builder()
                .currency(currency)
                .amount(amount)
                .accountNumber(accountNumber)
                .date(new Date())
                .type("DEBIT")
                .build();

        return t;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info ("Preparing repository...");

        repo.deleteAll();
        
        repo.save( buildTransaction("GBP", 935.0, 123) );
        repo.save( buildTransaction("GBP", 9351.0, 123) );
        repo.save( buildTransaction("GBP", 9235.0, 123) );

        repo.save( buildTransaction("GBP", 945.0, 124) );
        repo.save( buildTransaction("GBP", 94435.0, 124) );

        logger.info ("Repository initialization done.");
    }
}
