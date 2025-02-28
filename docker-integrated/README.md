# Running integrated environment

`docker-compose.yaml` has been defined to combine the following services:

* better-banking (core project)
* mongodb (used by better-banking)
* redis (used for caching)
* acme-banking
* acme-banking-db

`Keycloak` should run independently.

## Network

The docker compose file for the solution doesn't inclde Keycloak. To communicate with Keycloak as an OAuth server, a network needs to be added to enable the communicaton with better-banking as a client:

```yaml
networks:
  internal:
    name: better-banking
  external:
    name: docker-keycloak_default
    external: true
```

## Overriding environment variables

Service names and internal ports are used for inter container communication. 

```yaml
services:
  better-banking:
    environment:
      SPRING_DATA_MONGODB_HOST: mongodb
      SPRING_REDIS_HOST: redis
      # acme-banking
      SPRING_SECURITY_OAUTH2_CLIENT_PROVIDER_BETTER_BANKING_TOKEN_URI: http://acme-banking:8080/oauth/token
      SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_BETTER_BANKING_AUTHORISATION: "Basic YmV0dGVyLWJhbmtpbmc6N3lyYlI4WHBZNDViY0tQUA=="
      APPLICATION_OPEN_BANKING_BASE_URI: http://acme-banking:8080
      # keycloak
      JWK_SET_URI: http://kc:8080/realms/open-banking-realm/protocol/openid-connect/certs
```

# Keycloak roles

Ensure that realm `open-banking` roles are defined as assigned to the user as configured in the `application.yaml` of better-banking project (or overridden using the enviornment variables in the docker-compose.yaml file). Naming convention of `ROLE_1234567` is used for, let's say, account number `1234567`.