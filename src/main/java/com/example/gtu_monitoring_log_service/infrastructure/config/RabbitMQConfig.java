package com.example.gtu_monitoring_log_service.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.log}")
    private String logQueue;

    @Value("${rabbitmq.exchange.log}")
    private String logExchange;

    @Value("${rabbitmq.routingkey.log}")
    private String logRoutingKey;

    @Bean
    public Queue logQueue() {
        return new Queue(logQueue, true);
    }

    @Bean
    public DirectExchange logExchange() {
        return new DirectExchange(logExchange);
    }

    @Bean
    public Binding logBinding(Queue logQueue, DirectExchange logExchange) {
        return BindingBuilder.bind(logQueue).to(logExchange).with(logRoutingKey);
    }
}