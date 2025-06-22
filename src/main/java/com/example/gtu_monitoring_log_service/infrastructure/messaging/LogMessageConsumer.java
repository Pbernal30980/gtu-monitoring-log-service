package com.example.gtu_monitoring_log_service.infrastructure.messaging;

import com.example.gtu_monitoring_log_service.domain.model.LogEvent;
import com.example.gtu_monitoring_log_service.domain.service.LogService;
import com.example.gtu_monitoring_log_service.infrastructure.messaging.event.LogEventMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class LogMessageConsumer {

    private final LogService logService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "${rabbitmq.queue.log}")
    public void receiveMessage(String msg) {
        System.out.println("[DEBUG] Mensaje recibido de RabbitMQ: " + msg);
        try {
            LogEventMessage evt = objectMapper.readValue(msg, LogEventMessage.class);
            LogEvent log = new LogEvent();
            log.setTimestamp(Instant.parse(evt.getTimestamp()));
            log.setService(evt.getService());
            log.setLevel(evt.getLevel());
            log.setMessage(evt.getMessage());
            log.setDetails(evt.getDetails());
            System.out.println("[DEBUG] LogEvent a guardar: " + log);
            logService.processLog(log);
            System.out.println("[DEBUG] Log guardado exitosamente en MongoDB");
        } catch (Exception e) {
            System.err.println("[ERROR] Fallo al procesar el mensaje de log: " + e.getMessage());
            e.printStackTrace();
        }
    }
}