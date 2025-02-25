# Cache via redis
Standalone docker container of [redis image](https://hub.docker.com/_/redis) is required.

## Required dependencies
The code in this project uses the following libraries:

* `org.springframework.boot:spring-boot-data-redis`

## Integrations
Following services are required to fully run this Spring Boot app:

* Keycloak (docker-keycloak) for better-banking as resource server
* Mongodb (docker-mongodb) for local data repository
* Redis (docker-redis) for caching
* acme-bank (docker-manning-bank-testnet) for remote OpenAPI integration

## Application properties
The following properties are important for redis:

```yaml
spring:
  cache:
    type: redis
  redis:
    host: localhost
    port: 6379
```

## Cacheable
See code for the usage of @Cacheable annotation in `TransactionService`.