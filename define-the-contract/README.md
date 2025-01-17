# Transaction Processor (Spring Boot)

The code is based on Spring Boot. Some libraries used outside this context are covered below.

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


## Dependency management
For this to work, `build.gradle` needs to be updated:

```groovy
dependencies {
    // Lombok
    implementation 'org.projectlombok:lombok:1.18.36'
    annotationProcessor 'org.projectlombok:lombok'
}
```