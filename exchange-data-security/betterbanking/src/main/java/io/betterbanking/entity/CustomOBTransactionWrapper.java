package io.betterbanking.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
public class CustomOBTransactionWrapper {
    private TransactionData data;
    private Object links;
    private Object meta;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TransactionData {
        private List<CustomOBTransaction> transaction;
    }
}
