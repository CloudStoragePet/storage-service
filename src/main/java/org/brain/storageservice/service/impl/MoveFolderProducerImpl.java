package org.brain.storageservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.brain.storageservice.config.RabbitQueueProperties;
import org.brain.storageservice.model.MoveFolderTask;
import org.brain.storageservice.service.MoveFolderProducer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MoveFolderProducerImpl implements MoveFolderProducer {
    private final RabbitQueueProperties rabbitQueueProperties;
    private final RabbitTemplate rabbitTemplate;

    public MoveFolderTask sendMoveFolderMessage(MoveFolderTask message) {
        log.info("Sending message to queue: {}", message);
        // convert message into string json
        message.setStatus("PENDING");
        rabbitTemplate.convertAndSend(rabbitQueueProperties.exchangeName(), rabbitQueueProperties.routingKey(), message);
        log.info("Message sent to queue: {}", message);
        return message;
    }
}
