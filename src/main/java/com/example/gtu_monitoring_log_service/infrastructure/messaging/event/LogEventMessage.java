package com.example.gtu_monitoring_log_service.infrastructure.messaging.event;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter @Setter
public class LogEventMessage {
    private String timestamp;
    private String service;
    private String level;
    private String message;
    private Map<String, Object> details;
}