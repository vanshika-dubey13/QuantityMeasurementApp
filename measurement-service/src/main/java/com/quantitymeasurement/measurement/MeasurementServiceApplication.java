package com.quantitymeasurement.measurement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/*
 * =========================================================
 * Measurement Service Application
 * =========================================================
 *
 * Purpose:
 * Entry point for the Measurement Microservice.
 * This service handles all core quantity conversions and 
 * arithmetic logic (UC1 - UC18).
 */

@SpringBootApplication
@EnableDiscoveryClient // Enables this service to register with Eureka
public class MeasurementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeasurementServiceApplication.class, args);
        
        System.out.println("=========================================");
        System.out.println(" Measurement Service Started on Port 8082");
        System.out.println("=========================================");
    }
}