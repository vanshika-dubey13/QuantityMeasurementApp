# UC17: Spring Boot REST API with JPA Persistence for Quantity Measurement  
### Controller, Service, Repository, Entity and API Documentation Integration

---

## Description

UC17 enhances the **Quantity Measurement Application** by transforming it into a **full-fledged Spring Boot REST API** with **JPA-based database persistence**.

Building upon the layered architecture introduced in UC15 and database integration from UC16, this use case replaces manual JDBC handling with **Spring Data JPA**, enabling automatic database operations and cleaner code.

The application now exposes all quantity measurement operations (compare, convert, add, subtract, divide) as **RESTful APIs**, allowing clients such as **Swagger UI, Postman, or frontend applications** to interact with the system over HTTP.

UC17 also introduces **OpenAPI (Swagger) documentation**, enabling interactive API testing and improving developer experience.

The system now supports **persistent storage, API-based access, validation, exception handling, and structured response handling**, making it closer to a real-world enterprise backend service.

---

## Limitations in UC16

Before UC17:

1. Application used **JDBC with manual SQL queries**, increasing boilerplate code.
2. No **REST API exposure**, limiting usage to internal or console-based execution.
3. No **automatic API documentation** for developers.
4. No **standard HTTP response handling (status codes, JSON responses)**.
5. Validation and error handling were not fully aligned with web standards.
6. Tight coupling existed between layers due to manual database handling.
7. No support for **modern backend communication (REST over HTTP)**.

---

## Goals of UC17

- Introduce **Spring Boot REST APIs**
- Replace JDBC with **Spring Data JPA**
- Enable **automatic database operations (CRUD)**
- Provide **OpenAPI (Swagger) documentation**
- Implement **global exception handling**
- Support **input validation using annotations**
- Enable **JSON-based request and response handling**
- Improve **code maintainability and scalability**
- Provide **API endpoints for external clients**

---

## Layered Architecture

System Flow:

Client (Swagger / Postman)  
      ↓  
Controller Layer (REST APIs)  
      ↓  
DTO Layer (Request / Response Objects)  
      ↓  
Service Layer (Business Logic)  
      ↓  
Domain Logic (Quantity Engine)  
      ↓  
Entity Layer (JPA Entity Mapping)  
      ↓  
Repository Layer (Spring Data JPA)  
      ↓  
Database (MySQL / H2)

Each layer maintains a **clear separation of concerns**, ensuring modular and scalable architecture.

---

## REST Controller

UC17 introduces a **REST Controller**:

QuantityMeasurementController

Responsibilities include:

- Handling HTTP requests (GET, POST)  
- Validating input using annotations (`@Valid`)  
- Delegating operations to the Service Layer  
- Returning structured JSON responses  

Available APIs:

- POST `/compare` → Compare two quantities  
- POST `/convert` → Convert units  
- POST `/add` → Add quantities  
- POST `/subtract` → Subtract quantities  
- POST `/divide` → Divide quantities  
- GET `/history/type/{type}` → Fetch operation history  
- GET `/count/{operation}` → Get operation count  

---

## Service Layer

The **Service Layer** contains the core business logic:

QuantityMeasurementServiceImpl

Responsibilities:

- Convert input DTOs into domain objects  
- Perform measurement operations using Quantity engine  
- Handle unit conversion and validation  
- Save operation history to database  
- Return structured response DTOs  

Helper methods:

- `createQuantity()` → Converts input to domain object  
- `resolveUnit()` → Identifies correct unit type  

---

## Repository Layer

UC17 uses **Spring Data JPA Repository**:

IQuantityMeasurementRepository

Responsibilities:

- Perform CRUD operations automatically  
- Execute derived query methods  

Key methods:

- `save()` → Store operation result  
- `findByOperation()` → Fetch history by type  
- `countByOperationAndErrorFalse()` → Count successful operations  

Spring automatically generates implementations, reducing boilerplate code.

---

## Entity Layer

UC17 introduces a **JPA Entity**:

QuantityMeasurementEntity

Represents database table:

quantity_measurements

Fields include:

- id (Primary Key)  
- operand1  
- operand2  
- operation (Enum)  
- result  
- error (boolean flag)  

This enables structured and persistent storage of all operations.

---

## DTO Layer

UC17 separates internal logic from external communication using DTOs:

1. QuantityInputDTO (Request DTO)  
   - Contains input values, units, target unit, and operation type  

2. QuantityMeasurementDTO (Response DTO)  
   - Contains result, unit, operation, and message  

3. QuantityDTO (Internal DTO)  
   - Used within service layer for computation  

This ensures **clean data transfer and loose coupling between layers**.

---

## Exception Handling

UC17 introduces **Global Exception Handling**:

GlobalExceptionHandler

Responsibilities:

- Handle domain-specific exceptions  
- Return meaningful HTTP responses  
- Prevent raw stack traces from reaching clients  

Handled cases:

- Invalid unit input  
- Unsupported operations  
- General system errors  

---

## OpenAPI (Swagger Integration)

UC17 integrates **Swagger (OpenAPI)** for API documentation.

Features:

- Interactive API testing via browser  
- Automatic endpoint documentation  
- Request/response visualization  

Access URLs:

- Swagger UI: http://localhost:8080/swagger-ui.html  
- OpenAPI JSON: http://localhost:8080/v3/api-docs  

---

## Example Flow

Example operation:

1 ft + 12 in

Execution flow:

Client (Swagger/Postman)  
→ Controller (REST API)  
→ Service Layer  
→ Quantity Engine  
→ Repository (JPA save)  
→ Database  
→ Response DTO  
→ Client  

Database Entry:

operand1 = 1 FEET  
operand2 = 12 INCH  
operation = ADD  
result = 2 FEET  

Output:

{
  "result": 2,
  "unit": "FEET",
  "operation": "ADD",
  "message": "Addition successful"
}

---

## Conclusion

UC17 transforms the **Quantity Measurement Application** into a **modern RESTful backend system** using **Spring Boot and Spring Data JPA**.

This enhancement provides:

- Scalable API-based architecture  
- Persistent and structured data storage  
- Interactive API documentation  
- Clean separation of concerns  
- Robust exception handling and validation  

The application is now **enterprise-ready**, capable of integration with frontend applications, microservices, and external systems.
