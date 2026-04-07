package com.quantitymeasurement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*
 * =========================================================
 * Global Exception Handler
 * =========================================================
 *
 * Handles exceptions thrown across the entire application.
 *
 * Instead of returning raw stack traces or HTTP 500 errors,
 * this class converts exceptions into meaningful HTTP responses.
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    /*
     * Handles domain-specific exceptions
     */
    @ExceptionHandler(QuantityMeasurementException.class)
    public ResponseEntity<String> handleQuantityException( QuantityMeasurementException ex) {

        return new ResponseEntity<>(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    /*
     * Handles unsupported operations
     * Example: Temperature addition
     */
    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<String> handleUnsupportedOperation( UnsupportedOperationException ex) {

        return new ResponseEntity<>(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }
    

    /*
     * Catch-all handler for unexpected errors
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {

        return new ResponseEntity<>(
                "Unexpected Error Occurred: " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}