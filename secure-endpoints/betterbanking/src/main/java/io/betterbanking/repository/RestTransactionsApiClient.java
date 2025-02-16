package io.betterbanking.repository;

import com.acme.banking.model.OBTransaction6;
import io.betterbanking.adapters.acme.OBTransactionAdapter;
import io.betterbanking.entity.Transaction;

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
    public List<Transaction> findAllByAccountNumber(final Integer accountNumber) {
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
                .map (txn -> OBTransactionAdapter.adapt(txn))
                .toList();
    }
}
