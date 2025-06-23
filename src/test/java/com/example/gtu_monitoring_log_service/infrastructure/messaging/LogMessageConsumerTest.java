package com.example.gtu_monitoring_log_service.infrastructure.messaging;

import com.example.gtu_monitoring_log_service.domain.model.LogEvent;
import com.example.gtu_monitoring_log_service.domain.service.LogService;
import com.example.gtu_monitoring_log_service.infrastructure.messaging.event.LogEventMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class LogMessageConsumerTest {

    @Mock
    private LogService logService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private LogMessageConsumer logMessageConsumer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReceiveMessage_ProcessValidMessage() throws Exception {
        String validMessage = "{\"timestamp\":\"2025-06-22T10:00:00Z\",\"service\":\"test-service\",\"level\":\"INFO\",\"message\":\"Test message\",\"details\":{}}";
        LogEventMessage logEventMessage = new LogEventMessage();
        logEventMessage.setTimestamp("2025-06-22T10:00:00Z");
        logEventMessage.setService("test-service");
        logEventMessage.setLevel("INFO");
        logEventMessage.setMessage("Test message");

        when(objectMapper.readValue(validMessage, LogEventMessage.class)).thenReturn(logEventMessage);

        logMessageConsumer.receiveMessage(validMessage);

        verify(logService, times(1)).processLog(any(LogEvent.class));
    }

    @Test
    void testReceiveMessage_HandleInvalidMessage() throws Exception {
        String invalidMessage = "invalid-json";

        doThrow(new RuntimeException("Invalid JSON")).when(objectMapper).readValue(invalidMessage,
                LogEventMessage.class);

        logMessageConsumer.receiveMessage(invalidMessage);

        verify(logService, never()).processLog(any(LogEvent.class));
    }
}