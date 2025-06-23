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
        
        String collectionName = determineCollection(event);

        
        mongoTemplate.save(event, collectionName);

        // Señal de alerta
        if ("ERROR".equalsIgnoreCase(event.getLevel()) 
            || "CRITICAL".equalsIgnoreCase(event.getLevel())) {
            mongoTemplate.save(event, "critical-Logs");
        }
    }

    private String determineCollection(LogEvent event) {
        return event.getService() != null ? event.getService() : "default-logs";
    }
}