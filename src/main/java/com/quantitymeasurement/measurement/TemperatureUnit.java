package com.quantitymeasurement.measurement;



/*
 * =========================================================
 * TemperatureUnit Enum
 * =========================================================
 *
 * Represents temperature measurement units.
 *
 * Supported Units:
 *  - Celsius
 *  - Fahrenheit
 *  - Kelvin
 *
 * =========================================================
 * UC14 Concept
 * =========================================================
 *
 * Temperature conversions are NON-LINEAR.
 *
 * Unlike length/weight conversions which use a
 * multiplication factor, temperature conversions
 * require offset-based formulas.
 *
 * Example:
 * F = (C × 9/5) + 32
 *
 * Therefore the default conversion logic defined in
 * IMeasurable cannot be used.
 *
 * Custom conversion methods are implemented instead.
 */
public enum TemperatureUnit implements IMeasureable {

    CELSIUS("C"),
    FAHRENHEIT("F"),
    KELVIN("K");

    private final String symbol;

    TemperatureUnit(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Not used for temperature conversions.
     * Temperature requires formula-based conversions.
     */
    @Override
    public double getConversionFactor() {
        return 1.0; // not used
    }

 
    /*
     * =================================================
     * UC14 – Arithmetic Restriction
     * =================================================
     *
     * Temperature does not support arithmetic operations
     * such as addition or subtraction.
     *
     * Example:
     * 30°C + 20°C → meaningless
     */
    @Override
    public boolean supportsArithmetic() {
        return false; // UC14 default restriction
    }
    
    /**
     * Parses user input into a TemperatureUnit.
     */
    public static TemperatureUnit fromString(String input) {
        return switch (input.trim().toUpperCase()) {
            case "C", "CELSIUS" -> CELSIUS;
            case "F", "FAHRENHEIT" -> FAHRENHEIT;
            case "K", "KELVIN" -> KELVIN;
            default -> throw new IllegalArgumentException(
                "Invalid temperature unit. Use C, F, or K"
            );
        };
    }


    /**
     * Returns the symbol of the temperature unit.
     */
    @Override
    public String getUnitName() {
        return symbol;
    }


    /*
     * Converts temperature to base unit (Celsius).
     */
    @Override
    public double convertToBaseUnit(double value) {
        // Base = Celsius
        return switch (this) {
            case CELSIUS -> value;
            case FAHRENHEIT -> (value - 32) * 5 / 9;
            case KELVIN -> value - 273.15;
        };
    }
    /*
     * Converts from base unit (Celsius) to target unit.
     */
    @Override
    public double convertFromBaseUnit(double baseValue) {
        return switch (this) {
            case CELSIUS -> baseValue;
            case FAHRENHEIT -> baseValue * 9 / 5 + 32;
            case KELVIN -> baseValue + 273.15;
        };
    }
    /**
     * Explicitly block arithmetic operations on temperature.
     */
    @Override
    public void validateOperationSupport(String operation) {
        throw new UnsupportedOperationException(
            "Temperature does not support arithmetic operation: " + operation
        );
    }
}
