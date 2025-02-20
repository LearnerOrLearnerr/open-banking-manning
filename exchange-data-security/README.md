# Secure endpoints
This better-banking project covers the Spring Boot Starter Security package. The code in this project covers
both resource server, where authentication is through Keycloak, and client, where the application connects with an Open Banking a resource
server running in a container.

## Required dependencies
The code in this project uses the following libraries:

* `org.springframework.boot:spring-boot-starter-security`
* `org.springframework.boot:spring-boot-starter-oauth2-resource-server`
* `org.springframework.boot:spring-boot-starter-oauth2-client`

## Dependency on Keycloak
Keycloak can be run in a container. For this project, an `open-banking-realm` needs to be
defined, as explained in [Keycloak container details](https://github.com/LearnerOrLearnerr/open-banking-manning/tree/main/docker-keycloak).

Spring Boot and Spring Security based implementation will act as a  resource server, using
Keycloak password based user authentication.

> **Note**  
Spring Security v6+ doesn't use `KeycloakSecurityConfigurerAdapter`, which essentially changes the dependencies and usage of classes.


### Application properties
For Keycloak integration, a JWT URL is required to be configured:

```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          jwk-set-uri: http://localhost:9001/realms/open-banking-realm/protocol/openid-connect/certs
```

### Security filter chain
Please see `config.SecurityConfiguration` class in this project for building HTTPSecurity object.

## Exchange Data Security
Better-Banking uses API's exposed by "acme-banking". The containers for [acme-banking and
acme-banking-db services](https://github.com/LearnerOrLearnerr/open-banking-manning/tree/main/docker-manning-bank-testnet) are separately covered.

### Application properties
Registration and token URI details are required so that the better-banking, the client, can connect
with the services.

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          better-banking:
            grant_type: client_credentials
            authorisation: "Basic YmV0dGVyLWJhbmtpbmc6N3lyYlI4WHBZNDViY0tQUA=="
        provider:
          better-banking:
            token-uri: http://localhost:9080/oauth/token
```

### REST API calls as a client
The `repository.RestTransactionsApiClient` manages the following:

* Invokes acme-banking API to get the access_token
* Using the access_token, it invokes the API to securely get an account's transactions

# Test client
There are two scenarios where authNZ is involved:

* Invokation of better-banking API, where Keycloak's provided access_token is validated
* Internally, better-banking API implementation will get the access_token from the acme-banking server

## Better-banking as a resource server
The following command can be used for getting the access token from Keycloak:

```sh
curl -X POST "http://localhost:9001/realms/open-banking-realm/protocol/openid-connect/token" ^
-H "Content-Type: application/x-www-form-urlencoded" ^
  -d "client_id=open-banking" ^
  -d "client_secret=BxfDdnv47dGz3p9kzRenMx5NmqcYkfNi" ^
  -d "grant_type=password" ^
  -d "username=ragamuffin" ^
  -d "password=ragamuffin"
```

The `access_token` from the response should be taken out (copy-paste or use jq tool). The access token 
can be used to make the API call to the better-banking application:

```sh
curl http://localhost:8080/api/v1/transactions/123 ^
  -H "Authorization: Bearer "%access_token%
```

## Better-Banking as a client
Similar to the [curl commands covered earlier](https://github.com/LearnerOrLearnerr/open-banking-manning/tree/main/docker-manning-bank-testnet), better-banking invokes the acme-banking OAuth2 token API's.