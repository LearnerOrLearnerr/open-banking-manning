package io.betterbanking.config;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Convert JWT token into a collection of GrantedAuthority
 */
public class CustomJwtAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private final JwtGrantedAuthoritiesConverter defaultAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    private Logger logger = LoggerFactory.getLogger(CustomJwtAuthoritiesConverter.class);

    /**
     * Convert JWT token to a collection of GrantedAuthority
     * Extraction part of the token is
     * "realm_access": {
     *     "roles": [
     *       "offline_access",
     *       "default-roles-open-banking-realm",
     *       "uma_authorization",
     *       "ROLE_124"
     *     ]
     *   }
     */
    @Override
    public Collection<GrantedAuthority> convert (Jwt jwt) {
        Collection<GrantedAuthority> authorities = defaultAuthoritiesConverter.convert(jwt);

        // Extract realm_access roles
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess == null) return authorities;

        // roles
        List<String> roles = (List<String>) realmAccess.get("roles");
        if (roles == null) return authorities;

        logger.debug("{} roles in JWT found", roles.size());
        roles.stream().forEach(role -> logger.debug("Role found: {}", role));

        authorities.addAll (roles.stream()
                .map (role -> new SimpleGrantedAuthority(role))
                .toList());

        return authorities;
    }

    /**
     * Static method to generate the customer converter and return it as part of JwtAuthenticationConverter
     */
    public static JwtAuthenticationConverter getConverter () {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new CustomJwtAuthoritiesConverter());
        return converter;
    }
}
