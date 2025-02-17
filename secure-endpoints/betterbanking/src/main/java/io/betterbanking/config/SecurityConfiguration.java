package io.betterbanking.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    String jwkUri;

    /**
     * JWT decoder required for decoding and validation of Json Web Tokens (JWT)
     */
    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(this.jwkUri).build();
    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        logger.debug("Creating spring security filter chain");

        // specific API URL path requiring OAuth2
        http.securityMatcher("/api/v1/transactions/*")
                .authorizeHttpRequests( authorize -> authorize.anyRequest().authenticated())
                .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()));

        return http.build();
    }



}
