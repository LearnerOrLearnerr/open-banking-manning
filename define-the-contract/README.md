# Transaction Processor (Spring Boot)

The code is based on Spring Boot. Some libraries used outside this context are covered below.

## Lombok

[Project Lombok](https://projectlombok.org/features/) has been used for its excellent annotations. A few examples below.

```java
package org.betterbanking.entity;

improt lombok.Builder;

@Builder
public class Transaction {
    Double amount;
    String current;
}
```

For this to work, `build.gradle` needs to be updated:

```groovy
dependencies {
    // Lombok
    implementation 'org.projectlombok:lombok:1.18.36'
    annotationProcessor 'org.projectlombok:lombok'
}
```