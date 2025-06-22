package com.example.gtu_monitoring_log_service.infrastructure.config;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.data.mongodb.uri=mongodb://localhost:27017/testdb",
        "rabbitmq.queue.log=test-queue",
        "rabbitmq.exchange.log=test-exchange",
        "rabbitmq.routingkey.log=test-routing-key",
        "spring.rabbitmq.host=localhost",
        "spring.rabbitmq.port=5672"
})
class RabbitMQConfigTest {

    @Autowired
    private RabbitMQConfig config;

    @Value("${rabbitmq.queue.log}")
    private String logQueue;

    @Value("${rabbitmq.exchange.log}")
    private String logExchange;

    @Value("${rabbitmq.routingkey.log}")
    private String logRoutingKey;

    @Test
    void testQueueCreation() {
        Queue queue = config.logQueue();
        assertEquals(logQueue, queue.getName());
        assertTrue(queue.isDurable());
    }

    @Test
    void testExchangeCreation() {
        DirectExchange exchange = config.logExchange();
        assertEquals(logExchange, exchange.getName());
    }

    @Test
    void testBindingCreation() {
        Queue queue = config.logQueue();
        DirectExchange exchange = config.logExchange();
        Binding binding = config.logBinding(queue, exchange);
        assertEquals(logRoutingKey, binding.getRoutingKey());
    }
}
