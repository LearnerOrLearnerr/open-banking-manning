package io.betterbanking.adapters.acme;

import com.acme.banking.model.OBTransaction6;
import io.betterbanking.entity.Transaction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Optional;

/**
 * Adapter that accepts OBTransaction6 and returns Transaction
 */
public class OBTransactionAdapter {

    /**
     * Convert OBTransaction6 to Transaction
     */
    public static Transaction adapt(OBTransaction6 obTransaction) {

        BigDecimal amount = new BigDecimal(obTransaction.getAmount().getAmount());
        BigDecimal exchangeRate = (obTransaction.getCurrencyExchange() == null) ?
                BigDecimal.ONE : obTransaction.getCurrencyExchange().getExchangeRate();

        OffsetDateTime dt = Optional.ofNullable(obTransaction.getValueDateTime()).orElse(obTransaction.getBookingDateTime());
        Date valueDate = Date.from(dt.toInstant());

        String merchantName = obTransaction.getMerchantDetails() != null ?
                obTransaction.getMerchantDetails().getMerchantName() : null;

        Transaction txn = Transaction.builder()
                .accountNumber  (Integer.parseInt(obTransaction.getAccountId()))
                .type           (String.valueOf(obTransaction.getCreditDebitIndicator()))
                .currency       (obTransaction.getAmount().getCurrency())
                .merchantName   (merchantName)
                .date           (valueDate)
                .amount         (amount.multiply(exchangeRate).doubleValue())
                .build();

        return txn;

    }
}
