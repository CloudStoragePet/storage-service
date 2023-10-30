package org.brain.storageservice.config;


import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfiguration {
    private final RabbitQueueProperties queueProperties;


    /**
     * spring bean to create rabbitmq queue
     **/
    @Bean
    public Queue queue() {
        return new Queue(queueProperties.moveQueueName(), true);
    }

    /**
     * define exchange
     **/
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(queueProperties.exchangeName());
    }

    /**
     * bind queue to exchange
     **/
    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(queueProperties.routingKey());
    }
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    // Created automatically by autoconfig-> ConnectionFactory, RabbitTemplate, RabbitAdmin
}