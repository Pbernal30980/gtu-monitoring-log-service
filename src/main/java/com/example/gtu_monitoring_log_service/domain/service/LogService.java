package com.example.gtu_monitoring_log_service.domain.service;

import com.example.gtu_monitoring_log_service.domain.model.LogEvent;    

public interface LogService {
    void processLog(LogEvent event);
}