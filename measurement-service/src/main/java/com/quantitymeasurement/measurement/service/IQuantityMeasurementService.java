package com.quantitymeasurement.measurement.service;



import java.util.List;

import com.quantitymeasurement.measurement.dto.QuantityInputDTO;
import com.quantitymeasurement.measurement.dto.QuantityMeasurementDTO;
import com.quantitymeasurement.measurement.enums.OperationType;


/*
 * =========================================================
 * Quantity Measurement Service Interface
 * =========================================================
 *
 * UC17 – Service Layer Contract
 *
 * Purpose:
 * Defines all quantity operations exposed to the controller.
 *
 * Responsibilities:
 * - Compare quantities
 * - Convert units
 * - Perform arithmetic operations
 * - Return structured response DTO
 */

public interface IQuantityMeasurementService {

	/*
	 * Compare two quantities
	 */
	QuantityMeasurementDTO compare(QuantityInputDTO input);

	/*
	 * Convert quantity to another unit
	 */
	QuantityMeasurementDTO convert(QuantityInputDTO input);

	/*
	 * Add two quantities
	 */
	QuantityMeasurementDTO add(QuantityInputDTO input);

	/*
	 * Subtract two quantities
	 */
	QuantityMeasurementDTO subtract(QuantityInputDTO input);

	/*
	 * Divide two quantities
	 */
	QuantityMeasurementDTO divide(QuantityInputDTO input);
	
	List<QuantityMeasurementDTO> getByType(String type);

	long getCount(OperationType operation);
}