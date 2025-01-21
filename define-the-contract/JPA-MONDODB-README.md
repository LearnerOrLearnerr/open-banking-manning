# Spring Boot with JPA for mongodb

The mongodb container execution is as explained in the [docker-compose.yml](https://github.com/LearnerOrLearnerr/open-banking-manning/blob/main/docker-mongodb/README.md) README.md file.

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

## Adding a JPA respository

`MongoRepository` is the *interface* that needs to be extended for creating custom repositories to interact with MongoDB collections.

> Queries are automatically generated as long as the Spring Data naming conventions are followed.

Following example can fetch all items in the collection based on the account number (defined as an Integer):

```java
// Annotation required
// Defined as an interface only
@Repository
public interface TransactionRepository extends MongoRepository <Transaction, String> {
    // Custom query interface
    public List<Transaction> findAllByAccountNumber (final Integer accountNumber);
}
```
##