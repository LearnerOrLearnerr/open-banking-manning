# Secure endpoints
This project covers the Spring Boot Starter Security package. 

## Dependency on Keycloak
Usage is dependent on a separately running Keycloak server; a separate explanation is available for running a 
[Keycloak container](https://github.com/LearnerOrLearnerr/open-banking-manning/tree/main/docker-keycloak). It provides
an example of an `open-banking-realm` along with an active, enabled user for password based authentication.

As part of this Spring Boot and Spring Security, the resource server connects with the Keycloak container/ instance for
authentication.

> **Note**  
Spring Security v6+ doesn't use `KeycloakSecurityConfigurerAdapter`, which essentially changes the dependencies and usage of classes.

# Required dependencies
The code in this project uses the following libraries:

* `org.springframework.boot:spring-boot-starter-security`
* `org.springframework.boot:spring-boot-starter-oauth2-resource-server`

# Application properties
Only JWT URL is required to be configured:

```yaml
security:
oauth2:
  resourceserver:
    jwt:
      jwk-set-uri: http://localhost:9001/realms/open-banking-realm/protocol/openid-connect/certs
```

# Security filter chain
Please see `config.SecurityConfiguration` class for building HTTPSecurity object.

# Test client
The following command can be used for getting the access toke from Keycloak:

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
can be used to make the API call:

```sh
curl http://localhost:8080/api/v1/transactions/123 ^
  -H "Authorization: Bearer "%access_token%
```