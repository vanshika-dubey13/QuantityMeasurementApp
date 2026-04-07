package com.quantitymeasurement;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * =========================================================
 * Quantity Measurement Application
 * =========================================================
 *
 * UC17 – Spring Boot Application Entry Point
 *
 * Purpose:
 * Bootstraps the Spring Boot application and initializes
 * the Spring container.
 *
 * Responsibilities:
 * - Starts embedded Tomcat server
 * - Loads Spring configuration
 * - Enables component scanning
 * - Initializes controllers, services, repositories
 *
 * Application Flow (UC17):
 *
 *                         CLIENT
 *                 (Swagger / Postman)
 *                          |
 *                          |  HTTP Request (JSON)
 *                          |
 *               ----------------------------
 *               |        CONTROLLER        |
 *               | QuantityMeasurementCtrl  |
 *               ----------------------------
 *                          |
 *                          | Request Body
 *                          |
 *               ----------------------------
 *               |           DTO            |
 *               |   QuantityInputDTO       |
 *               ----------------------------
 *                          |
 *                          |
 *               ----------------------------
 *               |         SERVICE          |
 *               | QuantityMeasurementSvc   |
 *               | (Business Logic Layer)   |
 *               ----------------------------
 *                          |
 *                          | createQuantity()
 *                          |
 *               ----------------------------
 *               |     MEASUREMENT ENGINE   |
 *               | Quantity / Units / Enum  |
 *               | Length, Weight, Volume   |
 *               | Temperature              |
 *               ----------------------------
 *                          |
 *                          | Operation Result
 *                          |
 *               ----------------------------
 *               |          ENTITY          |
 *               | QuantityMeasurementEntity|
 *               ----------------------------
 *                          |
 *                          |
 *               ----------------------------
 *               |        REPOSITORY        |
 *               | Spring Data JPA          |
 *               | IQuantityMeasurementRepo |
 *               ----------------------------
 *                          |
 *                          |
 *                    -------------
 *                    | DATABASE  |
 *                    |  MySQL    |
 *                    -------------
 *                          |
 *                          |
 *               ----------------------------
 *               |        RESPONSE DTO      |
 *               | QuantityMeasurementDTO   |
 *               ----------------------------
 *                          |
 *                          |
 *               ----------------------------
 *               |       CONTROLLER         |
 *               |  Sends JSON Response     |
 *               ----------------------------
 *                          |
 *                          
 *                         CLIENT
 *
 * Important:
 * - No Scanner input
 * - No manual object creation
 * - Spring handles dependency injection
 *
 */

@SpringBootApplication
/* @Configuration
 * @EnableAutoConfiguration
 * @ComponentScan
 */
public class QuantityMeasurementApplication {

    public static void main(String[] args) {

        SpringApplication.run(
                QuantityMeasurementApplication.class,
                args
        );

        /*
        System.out.println("====================================");
        System.out.println("   Quantity Measurement API Started ");
        System.out.println("====================================");

        System.out.println("Swagger UI: http://localhost:8080/swagger-ui.html");
        */
        
        final Logger log = LoggerFactory.getLogger(QuantityMeasurementApplication.class);

        log.info("Quantity Measurement API Started");
        log.info("Swagger UI: http://localhost:8080/swagger-ui.html");
        log.info("http://localhost:8080/swagger-ui/index.html");
        log.info("Server running at: http://localhost:8080");
    }
}