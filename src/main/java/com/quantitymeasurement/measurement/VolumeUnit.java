package com.quantitymeasurement.measurement;


/*
 * =========================================================
 * VolumeUnit Enum
 * =========================================================
 *
 * Represents supported units for volume measurements.
 *
 * Base Unit: LITRE
 *
 * All volume units define their conversion factor relative
 * to the base unit (LITRE).
 *
 * Example conversions:
 * 1 litre      = 1000 millilitres
 * 1 gallon     = 3.78541 litres
 *
 * =========================================================
 * UC11 – Volume Support
 * =========================================================
 *
 * UC11 extended the system to support volume measurements
 * in addition to length and weight.
 *
 * This enum implements the IMeasurable interface so that
 * volume units can:
 * - Convert values to base units
 * - Convert values from base units
 * - Work with the generic Quantity class
 */
public enum VolumeUnit implements IMeasureable {
	
    /*
     * Enum constants representing volume units.
     * Each constant stores a conversion factor relative to litres.
     */

    LITRE(1.0),          // Base unit
    MILLILITRE(0.001),   // 1 ml = 0.001 litre
    GALLON(3.78541);     // 1 gallon = 3.78541 litres
	
	
    /*
     * Conversion factor used to convert this unit to litres.
     */
    private final double toLitre;

    VolumeUnit(double toLitre) {
        this.toLitre = toLitre;
    }
    
    
    /*
     * Returns the conversion factor relative to base unit.
     */
    @Override
    public double getConversionFactor() {
        return toLitre;
    }

    @Override
    public String getUnitName() {
        return name();
    }
    
    
    /*
     * Utility method to parse user input and convert
     * it to the corresponding VolumeUnit.
     *
     * Supported inputs:
     * l   -> LITRE
     * ml  -> MILLILITRE
     * gal -> GALLON
     */
    public static VolumeUnit fromString(String unit) {
        return switch (unit.trim().toLowerCase()) {
            case "l", "litre", "ltr" -> LITRE;
            case "ml", "milltr" -> MILLILITRE;
            case "gal" -> GALLON;
            default -> throw new IllegalArgumentException("Invalid volume unit");
        };
    }
}