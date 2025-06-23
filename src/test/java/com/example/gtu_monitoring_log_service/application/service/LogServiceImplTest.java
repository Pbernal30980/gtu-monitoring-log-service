package com.example.gtu_monitoring_log_service.application.service;

import com.example.gtu_monitoring_log_service.domain.model.LogEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.mockito.Mockito.*;

class LogServiceImplTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private LogServiceImpl logService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessLog_SaveToMongoDB() {
        LogEvent logEvent = new LogEvent();
        logEvent.setLevel("INFO");
        logService.processLog(logEvent);

        verify(mongoTemplate, times(1)).save(logEvent, "logs");
    }

    @Test
    void testProcessLog_SendAlertForCriticalLog() {
        LogEvent logEvent = new LogEvent();
        logEvent.setLevel("CRITICAL");
        logService.processLog(logEvent);

    }
}
