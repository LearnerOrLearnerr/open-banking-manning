package io.betterbanking.adapters;

import io.betterbanking.entity.CustomOBTransaction;
import io.betterbanking.entity.Transaction;

/**
 * Custom implementation of OBTransaction to local object model of betterbanking app
 */
public class CustomOBTransactionAdapter {
    public static Transaction adapt (CustomOBTransaction transaction) {
        Transaction result = Transaction.builder()
                .accountNumber(Integer.valueOf(transaction.getAccountId()))
                .amount(Double.valueOf(transaction.getAmount().getAmount()))
                .currency(transaction.getAmount().getCurrency())
                .id(transaction.getTransactionId())
                .merchantName(transaction.getMerchantDetails().getMerchantName())
                .build();
        return result;
    }
}
