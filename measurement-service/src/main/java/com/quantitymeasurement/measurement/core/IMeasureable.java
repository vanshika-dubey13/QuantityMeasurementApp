package com.quantitymeasurement.measurement.core;

/*
 * UC10 – UC14
 * -------------
 * IMeasurable is a common interface implemented by all measurable unit enums
 * such as LengthUnit, WeightUnit, VolumeUnit, and TemperatureUnit.
 *
 * Purpose:
 * - Provide a common contract for unit conversion.
 * - Ensure all units can convert values to a common base unit.
 * - Enable polymorphism so Quantity class can work with any unit type.
 *
 * Concepts used:
 * - Interface Segregation Principle (ISP)
 * - Default methods in interfaces
 * - Capability-based design
 */
public interface IMeasureable {

	/*
	 * Returns the conversion factor of the unit with respect to the base unit.
	 *
	 * Example for Length: Base unit = FEET 1 inch = 1/12 feet
	 *
	 * This method is implemented by each enum unit.
	 */
	double getConversionFactor();

	/*
	 * Converts a value from the current unit to the base unit.
	 *
	 * Example: value = 12 inches conversionFactor = 1/12
	 *
	 * baseValue = 12 * (1/12) = 1 foot
	 *
	 * Default implementation is provided because most units follow simple
	 * multiplication-based conversion.
	 */
	default double convertToBaseUnit(double value) {
		return value * getConversionFactor();
	}

	/*
	 * Converts a value from the base unit back to the current unit.
	 *
	 * Example: baseValue = 1 foot conversionFactor = 1/12
	 *
	 * result = 1 / (1/12) = 12 inches
	 *
	 * Default implementation works for linear conversions.
	 */
	default double convertFromBaseUnit(double baseValue) {
		return baseValue / getConversionFactor();
	}

	String getUnitName();

	/*
	 * ========================================= UC14 – Selective Arithmetic Support
	 * =========================================
	 */

	/*
	 * This method determines whether arithmetic operations are allowed for this
	 * unit type.
	 *
	 * Supported categories: - Length - Weight - Volume
	 *
	 * Not supported: - Temperature
	 *
	 * Example: 10 feet + 5 feet -> allowed 10°C + 5°C -> not meaningful -> not
	 * allowed
	 *
	 * By default arithmetic is allowed. TemperatureUnit overrides this method.
	 */
	default boolean supportsArithmetic() {
		return true;
	}

	/*
	 * Validates whether an operation is allowed for this unit.
	 *
	 * If arithmetic is not supported, this method throws an exception.
	 *
	 * Example: Temperature addition -> throws exception
	 */
	default void validateOperationSupport(String operation) {
		if (!supportsArithmetic()) {
			throw new UnsupportedOperationException(
					"Operation '" + operation + "' not supported for unit: " + getUnitName());
		}
	}
}
