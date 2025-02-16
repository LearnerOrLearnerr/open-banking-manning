package io.betterbanking.repository;

import io.betterbanking.entity.Transaction;

import java.util.List;

/**
 * OpenBanking client for transaction history
 */
public interface TransactionApiClient {
    public List<Transaction> findAllByAccountNumber(final Integer accountNumber);
}
