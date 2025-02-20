package io.betterbanking.web.dto;

import java.util.Date;

import io.betterbanking.entity.Transaction;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionDto {
    private String id;

    private String type;
    private Date date;
    private Integer accountNumber;
    private String currency;
    private Double amount;
    private String merchantName;
    private String merchantLogo;

    public static TransactionDto apply (final Transaction txn) {
        var dto = new TransactionDtoBuilder()
                .id(txn.getId())
                .type(txn.getType())
                .accountNumber(txn.getAccountNumber())
                .currency(txn.getCurrency())
                .amount(txn.getAmount())
                .merchantName(txn.getMerchantName())
                .merchantLogo(txn.getMerchantLogo())
                .build();

        return dto;
    }
}
