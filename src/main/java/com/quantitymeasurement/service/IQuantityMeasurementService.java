package com.quantitymeasurement.service;



import java.util.List;

import com.quantitymeasurement.dto.QuantityInputDTO;
import com.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.quantitymeasurement.enums.OperationType;

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