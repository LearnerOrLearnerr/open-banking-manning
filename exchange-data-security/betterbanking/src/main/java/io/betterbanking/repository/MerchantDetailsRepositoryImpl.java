package io.betterbanking.repository;

import java.util.Map;
import java.util.Optional;

public class MerchantDetailsRepositoryImpl implements MerchantDetailsRepository {

    Map<String, String> merchantMap;

    public void MerchantDetailsRepository() {
        merchantMap = Map.of (
                "acme", "acme-logo.png",
                "globex", "globex-logo.png",
                "morley", "morley-logo.png",
                "contoso", "contoso-logo.png"
        );
    }

    @Override
    public String findMerchantLogo(final String merchantName) {
        String logo = Optional.ofNullable(merchantMap.get(merchantName)).orElse("generic-logo.png");
        return logo;
    }
}
