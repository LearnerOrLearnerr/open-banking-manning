# Spring Boot with JPA for mongodb

The mongodb container execution is as explained in the [docker-compose.yml]() README.

## build.gradle

For using JPA for mongodb as part of Spring Boot, the following addition needs to be made to `build.gradle`:

```groovy
dependencies {
    implementation 'org.springframework:spring.boot.starter.data.mongodb'
}
```

## Using @Document annotation

Entity classes can be defined with @Document annotation. For mongodb, it's a collection, as shown in the example below:

```java
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.id;

// skipped showing import for lombok @Data and @Builder annotations

@Data
@Builder
@Document (collection = 'transactions')
public class Transaction {

    @Id
    private String id;

    private Double amount;
    private Data date;
    private String currency;
}

```