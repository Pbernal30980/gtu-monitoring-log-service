package com.example.gtu_monitoring_log_service;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class GtuMonitoringLogServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GtuMonitoringLogServiceApplication.class, args);
	}

}
