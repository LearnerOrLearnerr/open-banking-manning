package io.betterbanking.adapter;

import io.betterbanking.adapters.CustomOBTransactionAdapter;
import io.betterbanking.entity.CustomOBTransaction;

import io.betterbanking.entity.Transaction;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomOBTransactionAdapterTest {

    @Test
    public void shouldAddAllAttributes() {
        CustomOBTransaction.Amount amount = new CustomOBTransaction.Amount();
        amount.setAmount("35.5");
        amount.setCurrency("USD");

        CustomOBTransaction.MerchantDetails merchant = new CustomOBTransaction.MerchantDetails();
        merchant.setMerchantName("Apple");

        CustomOBTransaction obTxn = CustomOBTransaction.builder()
                .transactionId("123")
                .accountId("1234567")
                .amount(amount)
                .merchantDetails(merchant)
                .build();

        Transaction txn = CustomOBTransactionAdapter.adapt(obTxn);

        assertEquals (txn.getAccountNumber(),   Integer.valueOf(obTxn.getAccountId()));
        assertEquals (txn.getAmount(),          Double.valueOf(amount.getAmount()));
        assertEquals (txn.getCurrency(),        amount.getCurrency());
        assertEquals (txn.getMerchantName(),    merchant.getMerchantName());
    }
}
