package com.example.unit;

/**
 * SupportsArithmetic
 *
 * Marker interface used to indicate that a measurement unit supports arithmetic operations.
 *
 * A marker interface does not declare any methods. Instead, it acts as a tag
 * that can be checked at runtime to determine whether a particular unit allows
 * arithmetic operations.
 *
 * In the Quantity Measurement system, units that implement this interface can
 * participate in operations such as:
 * - Addition
 * - Subtraction
 * - Division
 *
 * Units that do not implement this interface are treated as non-arithmetic units.
 * For example, temperature units do not support arithmetic operations.
 *
 * The service layer verifies arithmetic capability using instanceof SupportsArithmetic.
 *
 * Example: LengthUnit, WeightUnit, and VolumeUnit implement this marker interface.
 *
 * @author Developer
 * @version 16.0
 * @since 1.0
 */
public interface SupportsArithmetic {
}