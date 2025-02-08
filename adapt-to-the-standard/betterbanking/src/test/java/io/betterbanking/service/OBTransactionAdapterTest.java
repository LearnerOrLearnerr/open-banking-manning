package io.betterbanking.service;

import com.acme.banking.model.*;
import io.betterbanking.entity.Transaction;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OBTransactionAdapterTest {

    private static OBTransactionAdapter adapter = new OBTransactionAdapter();

    @Test
    public void shouldAddAllAttributes() {
        String accountId = "123";
        OBCreditDebitCode1 crDr = OBCreditDebitCode1.DEBIT;
        ExternalEntryStatus1Code statusCode = ExternalEntryStatus1Code.BOOK;
        OffsetDateTime dt = OffsetDateTime.now();
        OBActiveOrHistoricCurrencyAndAmount9 amount = new OBActiveOrHistoricCurrencyAndAmount9 ("35", "GBP");

        OBTransaction6 obTxn = new OBTransaction6(accountId, crDr, statusCode, dt, amount);

        Transaction txn = adapter.toTransaction(obTxn);

        assertEquals (txn.getAccountNumber(),   Integer.valueOf(accountId));
        assertEquals (txn.getAmount(),          Double.valueOf(amount.getAmount()));
        assertEquals (txn.getCurrency(),        amount.getCurrency());
        assertEquals (txn.getType(),            crDr.toString());
    }
}
