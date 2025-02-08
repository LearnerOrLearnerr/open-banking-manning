package io.betterbanking.service;

import com.acme.banking.model.OBTransaction6;
import io.betterbanking.entity.Transaction;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.List;

/**
 * OpenBanking REST client to get list of transactions for an account
 */
@Component
public class RestTransactionsApiClient implements TransactionApiClient {

    @Override
    @CircuitBreaker(name = "transactionApiClient", fallbackMethod = "fallbackMethod")
    public List<Transaction> getTransactions(String accountNumber) {
        final String baseUrl = "http://localhost:8080/api/v1";
        final String uri = String.format ("/accounts/%s/transactions", accountNumber);

        RestClient restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();

        List<OBTransaction6> listTransactions = restClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<OBTransaction6>>() {});

        if (listTransactions == null || listTransactions.isEmpty()) return List.of();

        return listTransactions.stream()
                .map (txn -> OBTransactionAdapter.toTransaction(txn))
                .toList();
    }

    public String fallbackMethod(Throwable t) {
        return "{}";
    }
}
