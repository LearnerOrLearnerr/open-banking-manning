# Transaction Processor (Spring Boot)

The code is based on Spring Boot. Some libraries used outside this context are covered below.

- [Lombok](#lombok)
- [Spring Boot Test](#spring-boot-test)
- [Mock MVC](#mock-mvc)
- [rest-assured](#rest-assured)

## Lombok

[Project Lombok](https://projectlombok.org/features/) has been used for its excellent annotations. A few examples below.

```java
package org.betterbanking.entity;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class Transaction {
    Double amount;
    String current;
}
```

- `@Data` for getters, setters, toString(), etc. See the [lombok explanation](https://projectlombok.org/features/Data).
- `@Builder` for giving methods for setting up attributes, giving [object instantiation](https://projectlombok.org/features/Builder). 


### Dependency management
For this to work, `build.gradle` needs to be updated:

```groovy
// Lombok not required at runtime, compile time only
configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}

// Lombok dependencies
dependencies {
    compileOnly 'org.projectlombok:lombok:1.18.36'
    annotationProcessor 'org.projectlombok:lombok'
}
```

## Spring Boot Test

Similar to `@SpringBootApplication`, the `@SpringBootTest` annotation loads the entire Spring application context, which helps in writing integration tests.

It's not used for unit testing of a single class.

```java
@SpringBootTest
public class TransactionIntegrationTest {
    @Test
    public void testXyz() {
        assertTrue (1==1);
    }
}
```

### Dependency management
For this to work, `build.gradle` needs to be updated:

```groovy
dependencies {
    // Spring Boot Test
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```

## Mock MVC

Mock MVC is part of Spring Boot starter test. It's a Spring testing framework for HTTP requests and responses without the need for a running server. In short, it's suitable for unit testing


> Some other testing libraries that are part of `spring-boot-starter-test` are
> - JUnit
> - Mockito
> - Hamcrest

Following example shows `AutoConfigureMockMvc` and `Autowired` annotations.

```java
@SpringBootTest
@AutoConfigureMockMvc
public class TransactionIntegrationTest {
    
    @Autowired
    private MockMvc mvc;

    @Test
    public void testXyz() {
        mvc.perform (...);
    }
}
```

## rest-assured

It's a separate [library](https://github.com/rest-assured/), used for REST based integrated testing.

```groovy
dependencies {
    testImplementation 'io.rest-assured:rest-assured:5.5.0'
}
```

The following example shows the need to specify a web port for the rest-assured test client and the Spring application context:

```java
import static io.restassured.RestAssured.given;
import org.hamcrest.Mathcers;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, 
  classes = BetterBankingApplication.class)
public class TransactionIntegratedTest {
    
    @LocalServerPort
    private int port;

    @Test
    public void testTransaction () {
        String uri = String.format ("http://localhost:%d/api/v1/transaction", port);
        given().when()
            .get(uri)
            .then()
            .statisCode (Matchers.is(200));
    }
}
```