package com.quantitymeasurement.measurement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/*
 * =========================================================
 * OpenAPI / Swagger Configuration
 * =========================================================
 *
 * UC17 – API Documentation Layer
 *
 * Purpose:
 * Configures OpenAPI (Swagger) documentation for the
 * Quantity Measurement REST APIs.
 *
 * Responsibilities:
 * - Provide API metadata (title, version, description)
 * - Enable Swagger UI
 * - Allow developers to explore REST endpoints
 *
 * Access URLs:
 *
 * Swagger UI:
 * http://localhost:8080/swagger-ui.html
 *
 * OpenAPI JSON:
 * http://localhost:8080/v3/api-docs
 *
 * Benefits:
 * - Interactive API testing
 * - Automatic API documentation
 * - Developer-friendly interface
 */

@Configuration
public class OpenApiConfig {

	@Bean // Spring will create this object and manage it
    public OpenAPI quantityMeasurementAPI() {

        return new OpenAPI()
                /*
                 * =========================================================
                 * API INFORMATION (VISIBLE IN SWAGGER UI)
                 * =========================================================
                 */
                .info(new Info()
                        .title("Quantity Measurement API") // API name shown in Swagger
                        .description(
                                "REST API for performing quantity measurement operations "
                                        + "such as comparison, conversion, addition, subtraction, "
                                        + "and division across different measurement types."
                        ) // description at top
                        .version("1.0.0")  // version tag
                        .contact(new Contact()
                                .name("Quantity Measurement Team")  // contact info
                                .email("support@quantitymeasurement.com")
                        )
                        .license(new License()
                                .name("Apache 2.0") // license info
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")
                        )
                        
                    
                )
                /*
                 * =========================================================
                 *  SECURITY CONFIG (THIS ENABLES AUTHORIZE BUTTON)
                 * =========================================================
                 */
                // "All APIs require BearerAuth unless specified"
              .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
              
              /*
               * Define security scheme (JWT)
               */
              // defines What is BearerAuth?
              .components(new Components()
                  .addSecuritySchemes("BearerAuth",
                        new SecurityScheme()
                                .name("Authorization") // header name
                                .type(SecurityScheme.Type.HTTP)  // HTTP auth
                                .scheme("bearer") // Bearer token
                                .bearerFormat("JWT") // format is JWT
                   )
              );
    }
}