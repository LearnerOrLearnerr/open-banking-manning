package io.betterbanking.service;

import io.betterbanking.entity.Transaction;
import io.betterbanking.repository.MongoTransactionRepository;
import io.betterbanking.repository.TransactionApiClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    TransactionApiClient apiClient;

    @Autowired
    MongoTransactionRepository repo;

    @PostFilter(value = "hasRole('ROLE_' + filterObject.accountNumber)")
    @CircuitBreaker(name = "transactionService", fallbackMethod = "foundNone")
    public List<Transaction> findAllByAccountNumber(final Integer acctNr){

        List<Transaction> list = repo.findAllByAccountNumber(acctNr);
        if (list.size() == 0) throw new EmptyResultDataAccessException("Local data doesn't exist", -1);

        for (Transaction t : list) {
            t.setMerchantLogo(t.getMerchantName());
        }

        return list;
    }

    /**
     * Fallback method that checks local database for transaction data
     */
    public List<Transaction> foundNone(final Integer acctNr, Throwable t) {
        logger.error("Falling back to acme-banking API as no record found in the database (or connectivity issue)");
        List<Transaction> list = pollByAccountNumber(acctNr);
        return list;
    }

    /**
     * Find all distinct accounts numbers
     * @return
     */
    public List<Integer> findDistinctAccountNumbers() {
        return repo.findDistinctAccountNumbers();
    }

    /**
     * Poll transactions by account number
     */
    public List<Transaction> pollByAccountNumber (Integer acctNr) {
        logger.debug("Polling by account number {}", acctNr);

        // fetch transactions from acme-banking
        List<Transaction> remoteTransactions = apiClient.findAllByAccountNumber(acctNr);

        // check transactions in local database and insert if missing
        int count = 0;
        List<Transaction> localTransaction = repo.findAllByAccountNumber(acctNr);

        for (Transaction t : remoteTransactions) {
            if (!localTransaction.contains(t)) {

                logger.debug("Populating local database for transaction id {}, account {}, amount {} ",
                        t.getId(), t.getAccountNumber(), t.getAmount());

                repo.saveTransaction (t);
                ++count;
            }
            else {
                logger.debug("Matched acct {} and amount {}", t.getAccountNumber(), t.getAmount());
            }
        }

        logger.info("{} transactions added for account number {}", count, acctNr);
        return remoteTransactions;
    }
}