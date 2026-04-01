package com.app.quantitymeasurement.unit;

public final class Quantity<U extends IMeasureable> {

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        this.value = value;
        this.unit = unit;
    }

    public double getValue() { return value; }
    public U getUnit() { return unit; }
}