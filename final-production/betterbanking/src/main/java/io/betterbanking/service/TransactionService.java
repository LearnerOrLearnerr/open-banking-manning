package io.betterbanking.service;

import io.betterbanking.entity.Transaction;
import io.betterbanking.repository.MongoTransactionRepository;
import io.betterbanking.repository.TransactionApiClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@EnableCaching
public class TransactionService {

    Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    TransactionApiClient apiClient;

    @Autowired
    MongoTransactionRepository repo;

    @PostFilter(value = "hasRole('ROLE_' + filterObject.accountNumber)")
    @CircuitBreaker(name = "transactionService", fallbackMethod = "foundNone")
    @Cacheable (cacheNames="transactions")
    public List<Transaction> findAllByAccountNumber(final Integer acctNr){
        logger.info("Cache miss. Fetching transactions for account number {}", acctNr);

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
        List<Transaction> localTransaction = repo.findAllByAccountNumber(acctNr);

        AtomicInteger count = new AtomicInteger();
        remoteTransactions.stream()
                .filter(t -> !localTransaction.contains(t))
                .forEach (t -> {
                    repo.saveTransaction(t);
                    count.incrementAndGet();
                });

        logger.info("{} transactions added for account number {}", count.get(), acctNr);
        return remoteTransactions;
    }
}