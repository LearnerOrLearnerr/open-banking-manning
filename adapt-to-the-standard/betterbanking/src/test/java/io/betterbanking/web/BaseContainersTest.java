package io.betterbanking.web;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import java.time.Duration;

/**
 * Mongodb container start and stop is automatically handled by @Testcontainers
 * Dynamic property registry must be updated to define the container based connection string
 */
@Testcontainers
public class BaseContainersTest {

    public static final String MONGODB_IMAGE_NAME = "mongo:latest";
    public static final int INTERNAL_PORT = 27017;

    // Container definition is critical for Testcontainers
    @Container
    protected static MongoDBContainer mongoContainer = new MongoDBContainer (DockerImageName.parse(MONGODB_IMAGE_NAME))
            .withExposedPorts(INTERNAL_PORT);

    // Unable to use withCopyFileToContainer with Testcontainers, hence commented out
    /*
                .withCopyFileToContainer (MountableFile.forClasspathResource("./init.js"),
                    "/docker-entrypoint-initdb.d/init.js");

    */

    // Dynamic property registry update
    @DynamicPropertySource
    static void setMongoProperties (DynamicPropertyRegistry reg) {
        reg.add ("spring.data.mongodb.uri", mongoContainer::getConnectionString);
    }
}
