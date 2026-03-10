# UC15 – N-Tier Architecture Refactoring (Quantity Measurement Application)

## Overview
UC15 refactors the Quantity Measurement Application from a monolithic design into a **professional N-Tier architecture**.  
The application is organized into multiple layers to improve **separation of concerns, maintainability, scalability, and testability**.

## Architecture

The application follows a **layered architecture**:

```
Application Layer
        |
        v
Controller Layer
        |
        v
Service Layer
        |
        v
Repository Layer
        |
        v
Entity / Model Layer
```

## Layers Description

### Application Layer
Entry point of the application.

Class:
```
QuantityMeasurementApp
```

Responsibilities:
- Initialize components
- Start application
- Call controller methods

---

### Controller Layer
Handles user requests and delegates work to the service layer.

Class:
```
QuantityMeasurementController
```

Responsibilities:
- Accept input
- Call service methods
- Display results

---

### Service Layer
Contains the **core business logic** for quantity operations.

Interface:
```
IQuantityMeasurementService
```

Implementation:
```
QuantityMeasurementServiceImpl
```

Supported operations:
- Compare quantities
- Add quantities
- Subtract quantities
- Divide quantities

---

### Repository Layer
Responsible for storing measurement history.

Interface:
```
IQuantityMeasurementRepository
```

Implementation:
```
QuantityMeasurementCacheRepository
```

Uses **Singleton Pattern** to maintain a single repository instance.

---

### Entity / Model Layer

Classes used for data representation:

```
QuantityDTO
QuantityModel
QuantityMeasurementEntity
```

- **DTO** → Used for transferring data between layers  
- **Model** → Internal service representation  
- **Entity** → Stores measurement records  

---

## Design Principles Used

- Separation of Concerns
- SOLID Principles
- Dependency Injection

---

## Design Patterns Used

- Singleton Pattern
- Factory Pattern
- Facade Pattern

---

## Testing

JUnit test cases are implemented to verify:

- Service operations
- Controller flow
- Repository behavior
- Edge cases

---
