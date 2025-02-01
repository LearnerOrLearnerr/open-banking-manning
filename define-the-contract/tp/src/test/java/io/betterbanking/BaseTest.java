package io.betterbanking;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.images.builder.Transferable;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.MountableFile;

/**
 * BaseTest class for starting up the Testcontainers' mongodb container
 * It uses the configurational settings from application.yaml for username and password, etc.
 */
public class BaseTest {

    protected static final int INTERNAL_PORT = 27017;

    @Value ("${spring.data.mongodb.username}")
    protected static String DB_USERNAME;

    @Value ("${spring.data.mongodb.password}")
    protected static String DB_PASSWORD;

    @Value ("${spring.data.mongodb.database}")
    protected static String DB_NAME;

    @Container
    protected static MongoDBContainer mongo = new MongoDBContainer ("mongo:5.0.13")
        .withExposedPorts(INTERNAL_PORT)
        .withEnv ("MONGO_INITDB_ROOT_USERNAME", DB_USERNAME)
        .withEnv ("MONGO_INITDB_ROOT_PASSWORD", DB_PASSWORD)
        .withEnv ("MONGO_INITDB_DATABASE", DB_NAME)
        .withCopyFileToContainer ( 
            MountableFile.forClasspathResource("init.js"), 
            "/docker-entrypoint-initdb.d/init.js"
        );


    @DynamicPropertySource
    protected static void setProperties (DynamicPropertyRegistry reg) {
        reg.add("spring.data.mongodb.uri", mongo::getConnectionString);
    } 
    
    protected String getBaseUrl() {
        final int port = mongo.getFirstMappedPort();
        final String host = mongo.getHost();
        return String.format ("http://%s:%s/api/v1", host, port);
    }
}
