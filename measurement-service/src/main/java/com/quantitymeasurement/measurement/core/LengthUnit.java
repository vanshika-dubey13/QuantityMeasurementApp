package com.quantitymeasurement.measurement.core;



/*
 * LengthUnit enum represents different length measurement units.
 *
 * This enum implements the IMeasurable interface so that
 * all length units follow the same conversion contract.
 *
 * Base Unit: FEET
 *
 * All conversion factors are defined relative to FEET.
 *
 * Example conversions:
 * 1 foot  = 12 inches
 * 1 yard  = 3 feet
 * 1 foot  = 30.48 cm
 */
public enum LengthUnit implements IMeasureable {

    FEET(1.0),                // Base unit
    INCHES(1.0 / 12.0),       // 1 inch = 1/12 feet
    YARDS(3.0),               // 1 yard = 3 feet
    CENTIMETERS(1.0 / 30.48); // 1 cm = 1/30.48 feet

    private final double toFeet;

    LengthUnit(double toFeet) {
        this.toFeet = toFeet;
    }

    /*
     * Returns the conversion factor relative to the base unit.
     *
     * This method overrides the abstract method from IMeasurable.
     */
    @Override
    public double getConversionFactor() {
        return toFeet;
    }

    /*
     * Returns the name of the unit.
     *
     * Example:
     * FEET, INCHES, YARDS, CENTIMETERS
     */
    @Override
    public String getUnitName() {
        return name();
    }

    /*
     * Converts a string input to the corresponding LengthUnit.
     *
     * This method allows flexible input from users.
     *
     * Supported inputs:
     * ft, feet -> FEET
     * in, inch -> INCHES
     * yd, yard -> YARDS
     * cm, centimeter -> CENTIMETERS
     *
     * If the unit is invalid, an exception is thrown.
     */
    public static LengthUnit fromString(String unit) {
        return switch (unit.trim().toLowerCase()) {
            case "ft", "feet" -> FEET;
            case "in", "inch", "inches", "inc", "Inche", "Inches" -> INCHES;
            case "yd", "yard", "yrd" -> YARDS;
            case "cm", "centimeter", "cms" -> CENTIMETERS;
            default -> throw new IllegalArgumentException("Invalid length unit");
        };
    }
}