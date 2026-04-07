package com.quantitymeasurement.measurement.core;



/**
 * =========================================================
 * Base Measurement Class
 * =========================================================
 *
 * This abstract class represents the basic structure of any
 * measurable quantity in the system.
 *
 * Purpose:
 * - Provide a common parent for all measurement types.
 * - Store the numeric value associated with a measurement.
 *
 * Design Concepts:
 * - Encapsulation: Value is protected and immutable.
 * - Validation: Ensures only valid numeric values are stored.
 * - Reusability: Shared parent class for Quantity objects.
 *
 * Example:
 * Quantity<LengthUnit> extends Measurement
 */
public abstract class Measurement {

    protected final double value;

    /**
     * Constructor validates the measurement value.
     *
     * Ensures that the value is finite and not:
     * - NaN
     * - Infinity
     */
    protected Measurement(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be finite");
        }
        this.value = value;
    }

    /**
     * Returns the numeric value of the measurement.
     */
    public double getValue() {
        return value;
    }
}
