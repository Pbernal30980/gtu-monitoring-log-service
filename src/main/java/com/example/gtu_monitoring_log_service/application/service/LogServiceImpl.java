package com.example.gtu_monitoring_log_service.application.service;

import com.example.gtu_monitoring_log_service.domain.model.LogEvent;
import com.example.gtu_monitoring_log_service.domain.service.LogService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {
    private final MongoTemplate mongoTemplate;

    @Override
    public void processLog(LogEvent event) {
        // Almacena en colección "logs"
        mongoTemplate.save(event, "logs");
        // Si es ERROR o CRITICAL, lanza alerta (Slack, email…)
        if ("ERROR".equalsIgnoreCase(event.getLevel()) 
            || "CRITICAL".equalsIgnoreCase(event.getLevel())) {
            // TODO: integración con webhook de Slack o similar
        }
    }
}