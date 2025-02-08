package io.betterbanking.service;

import io.betterbanking.entity.Transaction;

import java.util.List;

/**
 * OpenBanking client for transaction history
 */
public interface TransactionApiClient {
    public List<Transaction> getTransactions (String accountNumber);
}
