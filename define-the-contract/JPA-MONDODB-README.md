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

## Testcontainers
Running a database engine similar to production in the test environment is possible with [Testcontainers](https://testcontainers.com/). In general, Testcontainers provide light-weight, disposable containers for testing.

Java based library for Testcontainer is discussed [here](https://java.testcontainers.org/). The build.gradle needs to include the following dependencies:

```groovy
dependencies {
    testImplementation platform('org.testcontainers:testcontainers-bom:1.20.4')
    testImplementation 'org.testcontainers:junit-jupiter'
    testImplementation 'org.testcnotainers:mongodb'
}
```

Following `@Testcontainer` annotated class runs with mongodb container:

```java
// package and imports skipped

@SpringBootTest (classes = BetterBankingApplication.class)
@Testcontainers
@AutoConfigureMockMvc
public class TransactionControllerIntegrationTestcontainers {

    // image name, along with the JS script for collection creation
    private static final String MONGODB_IMAGE_NAME = "mongo:4.0.10";
    private static final String INIT_SCRIPT_FILENAME = "init-script.js";

    // Testcontainers mongodb container instance
    private static MongoDBContainer mongoDB;

    @Autowired
    private MockMvc mvc;

    /**
     * Test class based on MockMvc
     */
    @Test
    public void testController () throws Exception {
        final String uri = "https://localhost:8080/api/v1/transactions/123";
        mvc.perform (get(uri))
                .andExpect(status().isOk());
    }

    @BeforeAll
    public static void beforeAll() {

        MountableFile initScript = forClasspathResource(INIT_SCRIPT_FILENAME);

        mongoDB = new MongoDBContainer(DockerImageName.parse(MONGODB_IMAGE_NAME)).
                withCopyFileToContainer(initScript, "/docker-entrypoint-initdb.d/");
        mongoDB.start();
    }

    @AfterAll
    public static void afterAll() {
        mongoDB.stop();
    }
}
```
