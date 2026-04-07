package com.quantitymeasurement.measurement.model;



import com.quantitymeasurement.measurement.core.IMeasureable;
import com.quantitymeasurement.measurement.core.Quantity;

import lombok.Getter;

/*
 * =========================================================
 * QuantityModel
 * =========================================================
 *
 * UC15 – Model Layer
 *
 * Purpose:
 * Internal representation of a quantity used inside
 * the Service layer.
 *
 * This separates internal processing from external DTOs.
 *
 * Model -> used for computations
 * DTO   -> used for communication
 */

@Getter
public class QuantityModel<U extends IMeasureable> {

    private Quantity<U> quantity;

    public QuantityModel(double value, U unit) {
        this.quantity = new Quantity<>(value, unit);
    }


    public double getValue() {
        return quantity.getValue();
    }

    public U getUnit() {
        return quantity.getUnit();
    }

    @Override
    public String toString() {
        return quantity.toString();
    }
}

