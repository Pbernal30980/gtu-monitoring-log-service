package com.example.gtu_monitoring_log_service.domain.model;

import lombok.Data;
import java.time.Instant;
import java.util.Map;

@Data
public class LogEvent {
    private Instant timestamp;
    private String service;
    private String level;       // ERROR, INFO, WARN…
    private String message;
    private Map<String, Object> details;
}