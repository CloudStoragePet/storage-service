package org.brain.storageservice;

import org.brain.storageservice.config.BasePathProperties;
import org.brain.storageservice.config.RabbitQueueProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({BasePathProperties.class, RabbitQueueProperties.class})

public class StorageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorageServiceApplication.class, args);
    }

}
