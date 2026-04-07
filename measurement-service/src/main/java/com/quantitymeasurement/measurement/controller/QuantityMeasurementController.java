package com.quantitymeasurement.measurement.controller;


import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.quantitymeasurement.measurement.dto.QuantityInputDTO;
import com.quantitymeasurement.measurement.dto.QuantityMeasurementDTO;
import com.quantitymeasurement.measurement.enums.OperationType;
import com.quantitymeasurement.measurement.service.IQuantityMeasurementService;



/*
 * =========================================================
 * Quantity Measurement REST Controller
 * =========================================================
 *
 * UC17 – REST Controller Layer
 *
 * Purpose:
 * Exposes quantity measurement operations as REST APIs.
 *
 * Responsibilities:
 * - Receive HTTP requests
 * - Validate request body
 * - Delegate operations to Service Layer
 * - Return JSON responses
 *
 * Base URL:
 * /api/v1/quantities
 *
 * Example Endpoints:
 *
 * POST /api/v1/quantities/compare
 * POST /api/v1/quantities/convert
 * POST /api/v1/quantities/add
 * POST /api/v1/quantities/subtract
 * POST /api/v1/quantities/divide
 */

@RestController
@RequestMapping("/api/v1/quantities")
public class QuantityMeasurementController {

    private final IQuantityMeasurementService service;

    /*
     * Constructor Injection
     */
    @Autowired
    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
    }
    
  /* =========================================================
   *     GET HISTORY BY OPERATION TYPE
   * =========================================================
   * 
   * Example:
   * GET /api/v1/quantities/history/type/ADD
   * 
   * 
   * Fetch all past operations of a specific type (ADD, SUBTRACT, etc.)
   */
    @GetMapping("/history/type/{type}")
    public ResponseEntity<List<QuantityMeasurementDTO>> getByType(
            @PathVariable String type) {

    	// Call service layer to fetch data from DB based on operation type
        List<QuantityMeasurementDTO> result = service.getByType(type);
        
     // Return HTTP 200 OK with list of results in JSON format
        return ResponseEntity.ok(result);
    }
    
    
    /* =========================================================
     *         GET COUNT OF OPERATIONS
     * =========================================================
     * 
     * Example:
     * GET /api/v1/quantities/count/ADD
     * 
     * Get total number of successful operations of a given type
     */

    @GetMapping("/count/{operation}")
    public ResponseEntity<Long> getCount( @PathVariable OperationType operation) {

        long count = service.getCount(operation);
        
         // Return count as response
        return ResponseEntity.ok(count);
    }

    /*
     * =========================================================
     * Compare Quantities
     * =========================================================
     * 
     * 1 foot == 12 inches -> TRUE
     * 
     * 
     * POST /compare
     * 
     */
    @PostMapping("/compare")
    public ResponseEntity<QuantityMeasurementDTO> compare(
    		@Valid @RequestBody QuantityInputDTO request) {

    	/*
    	 * @RequestBody -> Converts JSON input into Java object
    	 * @Valid -> Ensures input validation (like null, empty checks)
    	 */
        QuantityMeasurementDTO result = service.compare(request);
        
        // Return response with status 200 OK
        return ResponseEntity.ok(result);
    }

    /*
     * =========================================================
     * Convert Quantity
     * =========================================================
     *
     * Example:
     * 1 foot -> 12 inches
     *
     *  POST /convert
     */
    @PostMapping("/convert")
    public ResponseEntity<QuantityMeasurementDTO> convert(
            @RequestBody QuantityInputDTO request) {

        QuantityMeasurementDTO result = service.convert(request);
        
        // Return converted result
        return ResponseEntity.ok(result);
    }

    /*
     * =========================================================
     * Addition
     * =========================================================
     *
     * Example:
     * 1 foot + 12 inches = 2 feet
     *
     * Endpoint:
     * POST /add
     *
     */
    @PostMapping("/add")
    public ResponseEntity<QuantityMeasurementDTO> add(
            @RequestBody QuantityInputDTO request) {

        QuantityMeasurementDTO result = service.add(request);
        // Return result to client
        return ResponseEntity.ok(result);
    }

    /*
     * =========================================================
     * Subtraction
     * =========================================================
     *
     * Example:
     * 5 feet - 12 inches = 4 feet
     * 
     * Endpoint:
     * POST /subtract
     */
    @PostMapping("/subtract")
    public ResponseEntity<QuantityMeasurementDTO> subtract(
            @RequestBody QuantityInputDTO request) {

        QuantityMeasurementDTO result = service.subtract(request);
         // Return result
        return ResponseEntity.ok(result);
    }

    /*
     * =========================================================
     * Division
     * =========================================================
     *
     *
     * Example:
     * 10 feet / 5 feet = 2 (ratio)
     * 
     * POST /divide
     *
     */
    @PostMapping("/divide")
    public ResponseEntity<QuantityMeasurementDTO> divide(
            @RequestBody QuantityInputDTO request) {

        QuantityMeasurementDTO result = service.divide(request);
        
        // Return ratio result
        return ResponseEntity.ok(result);
    }
}