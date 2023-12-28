package org.brain.storageservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.brain.storageservice.config.KafkaProperties;
import org.brain.storageservice.model.MoveFolderTask;
import org.brain.storageservice.model.enums.MoveFolderTaskStatus;
import org.brain.storageservice.service.MoveFolderProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MoveFolderProducerImpl implements MoveFolderProducer {
    private final KafkaProperties rabbitQueueProperties;
    private final KafkaTemplate<String, MoveFolderTask> kafkaTemplate;
    @SneakyThrows
    public MoveFolderTask sendMoveFolderMessage(MoveFolderTask message) {
        log.info("Sending message to kafka: {}", message);
        // convert message into string json
        message.setStatus(MoveFolderTaskStatus.CREATED);
        kafkaTemplate.send(rabbitQueueProperties.topicName(), message);
        log.info("Message sent to kafka: {}", message);
        return message;
    }
}
