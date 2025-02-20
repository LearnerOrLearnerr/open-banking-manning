package io.betterbanking.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.betterbanking.adapters.CustomOBTransactionAdapter;
import io.betterbanking.entity.CustomOBTransaction;
import io.betterbanking.entity.CustomOBTransactionWrapper;
import io.betterbanking.entity.Transaction;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * OpenBanking REST client to get list of transactions for an account
 * Due to the OpenBanking dummy server being on a different version of OpenBanking, a CustomOBTransactionAdapter
 * had to be written. The object model is based on CustomOBTransaction and its wrapper.
 */
@Component
public class RestTransactionsApiClient implements TransactionApiClient {

    private final Logger logger = LoggerFactory.getLogger(RestTransactionsApiClient.class);

    @Value("${spring.security.oauth2.client.provider.better-banking.token-uri}")
    private String oAuthUrl;

    @Value ("${spring.security.oauth2.client.registration.better-banking.grant_type}")
    private String grantType;

    @Value ("${spring.security.oauth2.client.registration.better-banking.authorisation}")
    private String authorisation;

    @Value("${application.open-banking.base-uri}")
    private String openBankingUri;

    @PostConstruct
    public void init () {
        logger.info ("Grant type:    {}", grantType);
        logger.info ("Authorization: {}", authorisation);
        logger.info ("OAuth URL:     {}", oAuthUrl);
    }

    /**
     * Get auth token for OpenBanking API
     * @return
     */
    public String getOpenBankingAuthToken () throws JsonProcessingException {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", grantType);

        RestClient client = RestClient.builder()
                .baseUrl(oAuthUrl)
                .build();

        String resp = client.post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Authorization", authorisation)
                .body (formData)
                .retrieve()
                .body(String.class);

        ObjectMapper mapper = new ObjectMapper();
        TokenResponse tokenResponse  = mapper.readValue(resp, TokenResponse.class);
        return tokenResponse.getAccess_token();
    }

    @Override
    public List<Transaction> findAllByAccountNumber(final Integer accountNumber) {

        // access_token
        logger.info("Fetching token");
        String accessToken;
        try {
            accessToken = getOpenBankingAuthToken();
        }
        catch (JsonProcessingException ex) {
            System.err.println(ex.toString());
            return List.of();
        }

        // Fetch transactions list
        final String uri = String.format("%s/accounts/%d/transactions", openBankingUri, accountNumber);
        logger.info("Fetching transaction from {}", uri);

        RestClient client = RestClient.builder().build();
        String jsonResponse = client.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .body(String.class);

        // Mapper from json response to TransactionWrapper
        logger.info("Mapping JSON response to object model");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure (MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        try {
            CustomOBTransactionWrapper wrapper = mapper.readValue(jsonResponse, CustomOBTransactionWrapper.class);
            List<CustomOBTransaction> listTransactions = wrapper.getData().getTransaction();
            if (listTransactions == null || listTransactions.isEmpty()) return List.of();

            return listTransactions.stream()
                    .map (CustomOBTransactionAdapter::adapt)
                    .toList();
        }
        catch (JsonProcessingException ex) {
            System.err.println(ex.toString());
            return List.of();
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class TokenResponse {
        String access_token;
    }
}
