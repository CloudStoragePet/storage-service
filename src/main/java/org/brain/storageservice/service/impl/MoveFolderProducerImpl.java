package org.brain.storageservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    ObjectMapper objectMapper = new ObjectMapper();

    public MoveFolderTask sendMoveFolderMessage(MoveFolderTask message) {
        log.info("Sending message to queue: {}", message);
        // convert message into string json
        try {
            rabbitTemplate.convertAndSend(rabbitQueueProperties.exchangeName(), rabbitQueueProperties.routingKey(), objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("Message sent to queue: {}", message);
        return message;
    }
}
