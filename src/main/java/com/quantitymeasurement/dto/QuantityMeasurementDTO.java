package com.quantitymeasurement.dto;


import com.quantitymeasurement.enums.OperationType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * =========================================================
 * QuantityMeasurementDTO
 * =========================================================
 *
 * UC17 – API Response DTO
 *
 * Represents the result returned to the client.
 * 
 * Client (Swagger/Postman)
 *       │
 *       │ JSON Request
 *       
 *  QuantityInputDTO
 *       │
 *       
 *  Controller
 *       │
 *       
 *  Service Layer
 *       │
 *       
 *  QuantityDTO  (internal conversion logic)
 *       │
 *       
 *  Entity + Repository (DB)
 *       │
 *       
 *  QuantityMeasurementDTO
 *       │
 *       
 *  Controller
 *       │
 *       
 * JSON Response to Client
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuantityMeasurementDTO {

    private double result;
    private String unit;
    private OperationType operation;
    private String message;



}