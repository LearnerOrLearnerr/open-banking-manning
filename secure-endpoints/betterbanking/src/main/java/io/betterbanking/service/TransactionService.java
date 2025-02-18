package io.betterbanking.service;

import com.nimbusds.jose.proc.SecurityContext;
import io.betterbanking.entity.Transaction;
import io.betterbanking.repository.TransactionApiClient;
import io.betterbanking.repository.TransactionRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    TransactionApiClient apiClient;

    @Autowired
    TransactionRepository repo;

    @PostFilter(value = "hasRole('ROLE_' + filterObject.accountNumber)")
    @CircuitBreaker(name = "transactionService", fallbackMethod = "foundNone")
    public List<Transaction> findAllByAccountNumber(final Integer acctNr){

        List<Transaction> list = apiClient.findAllByAccountNumber(acctNr);

        for (Transaction t : list) {
            t.setMerchantLogo(t.getMerchantName());
        }

        return list;
    }

    /**
     * Fallback method that checks local database for transaction data
     */
    public List<Transaction> foundNone(final Integer acctNr, Throwable t) {
        logger.error("Falling back to local database");
        List<Transaction> list = repo.findAllByAccountNumber(acctNr);
        return list;
    }
}