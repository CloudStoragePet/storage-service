package org.brain.storageservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.kafka")
public record KafkaProperties(String topicName) {
}
