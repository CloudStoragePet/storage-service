package org.brain.storageservice.config;


import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfiguration {
    private final RabbitQueueProperties queueProperties;


    // spring bean for rabbitmq queue
    @Bean
    public Queue queue() {
        return new Queue(queueProperties.moveQueueName(), true);
    }

    // define exchange
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(queueProperties.exchangeName());
    }

    // bind queue to exchange
    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(queueProperties.routingKey());
    }

    // Created automatically by autoconfig-> ConnectionFactory, RabbitTemplate, RabbitAdmin
}