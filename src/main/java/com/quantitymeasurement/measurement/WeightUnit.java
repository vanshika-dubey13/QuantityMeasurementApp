package com.quantitymeasurement.measurement;




/*
 * =========================================================
 * WeightUnit Enum
 * =========================================================
 *
 * Represents supported weight measurement units.
 *
 * Base Unit: KILOGRAM
 *
 * Each unit defines a conversion factor relative
 * to kilograms.
 *
 * Example conversions:
 * 1 kilogram = 1000 grams
 * 1 pound    = 0.453592 kilograms
 *
 * =========================================================
 * UC11 – Weight Measurement Support
 * =========================================================
 *
 * Weight units implement the IMeasurable interface,
 * allowing them to integrate with the generic Quantity
 * class for:
 *
 * - unit conversion
 * - equality comparison
 * - arithmetic operations
 */
public enum WeightUnit implements IMeasureable {

    KILOGRAM(1.0),     // Base unit
    GRAM(0.001),       // 1 g = 0.001 kg
    POUND(0.453592);   // 1 lb = 0.453592 kg
	
	
	/*
     * Conversion factor relative to kilograms.
     */
    private final double toKg;

    WeightUnit(double toKg) {
        this.toKg = toKg;
    }
    
    
    /*
     * Returns the conversion factor relative to the base unit.
     */
    @Override
    public double getConversionFactor() {
        return toKg;
    }

    @Override
    public String getUnitName() {
        return name();
    }
    
    
    /*
     * Converts user input string to WeightUnit.
     *
     * Supported inputs:
     * kg -> KILOGRAM
     * g  -> GRAM
     * lb -> POUND
     */
    public static WeightUnit fromString(String unit) {
        return switch (unit.trim().toLowerCase()) {
            case "kg" -> KILOGRAM;
            case "g", "gm" -> GRAM;
            case "lb" -> POUND;
            default -> throw new IllegalArgumentException("Invalid weight unit");
        };
    }
}