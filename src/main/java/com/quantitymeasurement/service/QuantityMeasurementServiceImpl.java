package com.quantitymeasurement.service;



import com.quantitymeasurement.dto.QuantityInputDTO;
import com.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.quantitymeasurement.enums.OperationType;
import com.quantitymeasurement.exception.QuantityMeasurementException;
import com.quantitymeasurement.measurement.*;
import com.quantitymeasurement.repository.IQuantityMeasurementRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * =========================================================
 * Quantity Measurement Service Implementation
 * =========================================================
 *
 * UC17 – Service Layer
 *
 * This class contains the BUSINESS LOGIC of the application.
 *
 * Responsibilities:
 * 1. Receive request DTO from Controller
 * 2. Convert request data into Quantity domain objects
 * 3. Perform measurement operations (add, subtract, convert etc.)
 * 4. Save operation history to the database
 * 5. Return response DTO to Controller
 *
 * FLOW OF EXECUTION
 *
 * Client (Swagger/Postman)
 *        |
 *        
 *        
 * Controller receives QuantityInputDTO
 *        │
 *        
 * Service converts DTO -> Quantity Objects
 *        │
 *        
 * Perform requested operation
 *        │
 *        
 * Save operation to database
 *        │
 *        
 * Create response DTO
 *        │
 *        
 * Return response to Controller
 *
 */
@Transactional
@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

	/*
     * Repository used to store operation history in database
     */
    @Autowired
    private IQuantityMeasurementRepository repository;


    /*
     * =========================================================
     * METHOD: compare()
     * =========================================================
     *
     * PURPOSE:
     * Compare two quantities and determine whether they are equal.
     *
     * Example:
     * 1 foot == 12 inches -> TRUE
     *
     * STEPS:
     * 1. Convert DTO input into Quantity objects
     * 2. Use Quantity.equals() for comparison
     * 3. Save comparison result in database
     * 4. Return result DTO
     */
    @Override
    public QuantityMeasurementDTO compare(QuantityInputDTO input) {

    	// Convert request values into Quantity domain objects
        Quantity<IMeasureable> q1 = createQuantity(input.getValue1(), input.getUnit1());
        Quantity<IMeasureable> q2 = createQuantity(input.getValue2(), input.getUnit2());

        // Convert request values into Quantity domain objects
        boolean result = q1.equals(q2);

        // Store operation in database
        repository.save(new QuantityMeasurementEntity(
                q1.toString(),
                q2.toString(),
                OperationType.COMPARE,
                String.valueOf(result)
        ));

        // Create response DTO
        return new QuantityMeasurementDTO(
                result ? 1 : 0,
                "BOOLEAN",
                OperationType.COMPARE,
                "Comparison successful"
        );
    }

    /*
     * =========================================================
     * METHOD: convert()
     * =========================================================
     *
     * PURPOSE:
     * Convert a quantity from one unit to another.
     *
     * Example:
     * 1 foot -> 12 inches
     *
     * STEPS:
     * 1. Create Quantity object from input
     * 2. Resolve target unit
     * 3. Perform conversion
     * 4. Save conversion in database
     * 5. Return response DTO
     */
    @Override
    public QuantityMeasurementDTO convert(QuantityInputDTO input) {

    	// Create Quantity from input
        Quantity<IMeasureable> quantity =
                createQuantity(input.getValue1(), input.getUnit1());

        // Identify target unit
        IMeasureable targetUnit =
                resolveUnit(input.getTargetUnit());

        // Perform conversion
        Quantity<IMeasureable> converted =
                quantity.convertTo(targetUnit);

        // Save conversion operation
        repository.save(new QuantityMeasurementEntity(
                quantity.toString(),
                targetUnit.getUnitName(),
                OperationType.CONVERT,
                converted.toString()
        ));

        // Create response DTO
        return new QuantityMeasurementDTO(
                converted.getValue(),
                converted.getUnit().getUnitName(),
                OperationType.CONVERT,
                "Conversion successful"
        );
    }

    /*
     * =========================================================
     * METHOD: add()
     * =========================================================
     *
     * PURPOSE:
     * Add two quantities of the same measurement type.
     *
     * Example:
     * 1 foot + 12 inches = 2 feet
     *
     * STEPS:
     * 1. Convert input values into Quantity objects
     * 2. Perform addition using Quantity.add()
     * 3. Save operation history
     * 4. Return result DTO
     */
    @Override
    public QuantityMeasurementDTO add(QuantityInputDTO input) {

    	// Create quantity objects
        Quantity<IMeasureable> q1 = createQuantity(input.getValue1(), input.getUnit1());
        Quantity<IMeasureable> q2 = createQuantity(input.getValue2(), input.getUnit2());

        // Create quantity objects
        Quantity<IMeasureable> result = q1.add(q2);

        // Save operation in database
        repository.save(new QuantityMeasurementEntity(
                q1.toString(),
                q2.toString(),
                OperationType.ADD,
                result.toString()
        ));

        // Return response DTO
        return new QuantityMeasurementDTO(
                result.getValue(),
                result.getUnit().getUnitName(),
                OperationType.ADD,
                "Addition successful"
        );
    }

    /*
     * =========================================================
     * METHOD: subtract()
     * =========================================================
     *
     * PURPOSE:
     * Subtract one quantity from another.
     *
     * Example:
     * 5 feet - 12 inches = 4 feet
     *
     * STEPS:
     * 1. Create Quantity objects
     * 2. Perform subtraction
     * 3. Save result in database
     * 4. Return response DTO
     */
    @Override
    public QuantityMeasurementDTO subtract(QuantityInputDTO input) {

        Quantity<IMeasureable> q1 = createQuantity(input.getValue1(), input.getUnit1());
        Quantity<IMeasureable> q2 = createQuantity(input.getValue2(), input.getUnit2());

        // Perform subtraction
        Quantity<IMeasureable> result = q1.subtract(q2);

        // Perform subtraction
        repository.save(new QuantityMeasurementEntity(
                q1.toString(),
                q2.toString(),
                OperationType.SUBTRACT,
                result.toString()
        ));

        // Return response DTO
        return new QuantityMeasurementDTO(
                result.getValue(),
                result.getUnit().getUnitName(),
                OperationType.SUBTRACT,
                "Subtraction successful"
        );
    }

    /*
     * =========================================================
     * METHOD: divide()
     * =========================================================
     *
     * PURPOSE:
     * Divide one quantity by another.
     *
     * Example:
     * 10 feet / 5 feet = 2
     *
     * Result is dimensionless (ratio).
     *
     * STEPS:
     * 1. Create Quantity objects
     * 2. Perform division
     * 3. Save operation history
     * 4. Return response DTO
     */
    @Override
    public QuantityMeasurementDTO divide(QuantityInputDTO input) {

        Quantity<IMeasureable> q1 = createQuantity(input.getValue1(), input.getUnit1());
        Quantity<IMeasureable> q2 = createQuantity(input.getValue2(), input.getUnit2());

        // Perform division
        double result = q1.divide(q2);

        // Perform division
        repository.save(new QuantityMeasurementEntity(
                q1.toString(),
                q2.toString(),
                OperationType.DIVIDE,
                String.valueOf(result)
        ));

        // Return response DTO
        return new QuantityMeasurementDTO(
                result,
                "RATIO",
                OperationType.DIVIDE,
                "Division successful"
        );
    }


    /*
     * =========================================================
     * HELPER METHOD: createQuantity()
     * =========================================================
     *
     * PURPOSE:
     * Convert raw input (value + unit string)
     * into a Quantity object.
     *
     * Example:
     * (5, "feet") -> Quantity(5 FEET)
     *
     * This method determines which unit category the input
     * belongs to:
     *
     * - Length
     * - Weight
     * - Volume
     * - Temperature
     *
     * If the unit does not belong to any category,
     * a QuantityMeasurementException is thrown.
     */
    private Quantity<IMeasureable> createQuantity(double value, String unit) {

        unit = unit.toLowerCase();

        try {
            return new Quantity<>(value, LengthUnit.fromString(unit));
        } catch (Exception ignored) {}

        try {
            return new Quantity<>(value, WeightUnit.fromString(unit));
        } catch (Exception ignored) {}

        try {
            return new Quantity<>(value, VolumeUnit.fromString(unit));
        } catch (Exception ignored) {}

        try {
            return new Quantity<>(value, TemperatureUnit.fromString(unit));
        } catch (Exception ignored) {}

        throw new QuantityMeasurementException("Invalid unit: " + unit);
    }

    /*
     * =========================================================
     * HELPER METHOD: resolveUnit()
     * =========================================================
     *
     * PURPOSE:
     * Identify the correct unit object from a unit string.
     *
     * Used mainly in conversion operations.
     *
     * Example:
     * "inch" -> LengthUnit.INCHES
     * "kg"   -> WeightUnit.KILOGRAM
     *
     * If the unit is invalid, an exception is thrown.
     */
    private IMeasureable resolveUnit(String unit) {

        unit = unit.toLowerCase();

        try {
            return LengthUnit.fromString(unit);
        } catch (Exception ignored) {}

        try {
            return WeightUnit.fromString(unit);
        } catch (Exception ignored) {}

        try {
            return VolumeUnit.fromString(unit);
        } catch (Exception ignored) {}

        try {
            return TemperatureUnit.fromString(unit);
        } catch (Exception ignored) {}

        throw new QuantityMeasurementException("Invalid target unit: " + unit);
    }
    
    
    @Override
    public List<QuantityMeasurementDTO> getByType(String type) {

        OperationType op = OperationType.valueOf(type.toUpperCase());

        return repository.findByOperation(op)
                .stream()
                .map(entity -> new QuantityMeasurementDTO(
                        Double.parseDouble(entity.getResult()),
                        "N/A",
                        entity.getOperation(),
                        "Fetched from history"
                ))
                .toList();
    }

    @Override
    public long getCount(OperationType operation) {
        return repository.countByOperationAndErrorFalse(operation);
    }
}