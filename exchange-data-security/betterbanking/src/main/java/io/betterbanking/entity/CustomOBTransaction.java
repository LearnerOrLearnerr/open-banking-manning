package io.betterbanking.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomOBTransaction {
    private String accountId;
    private String transactionId;
    private Object transactionReference;
    private Object statementReference;
    private Object creditDebitIndicator;
    private Object status;
    private Object transactionMutability;
    private String bookingDateTime;
    private Object valueDateTime;
    private Object transactionInformation;
    private Object addressLine;
    private Amount amount;
    private Object chargeAmount;
    private CurrencyExchange currencyExchange;
    private Object bankTransactionCode;
    private Object proprietaryBankTransactionCode;
    private Object balance;
    private MerchantDetails merchantDetails;
    private Object creditorAgent;
    private Object creditorAccount;
    private Object debtorAgent;
    private Object debtorAccount;
    private Object cardInstrument;
    private Object supplementaryData;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Amount {
        private String amount;
        private String currency;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CurrencyExchange {
        private String sourceCurrency;
        private String targetCurrency;
        private String unitCurrency;
        private int exchangeRate;
        private Object contractIdentification;
        private Object quotationDate;
        private Object instructedAmount;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MerchantDetails {
        private String merchantName;
        private String merchantCategoryCode;
    }
}
