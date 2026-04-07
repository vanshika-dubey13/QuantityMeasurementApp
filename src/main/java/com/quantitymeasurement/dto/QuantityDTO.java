package com.quantitymeasurement.dto;


import com.quantitymeasurement.measurement.IMeasureable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * =========================================================
 * QuantityDTO
 * =========================================================
 *
 * UC17 – DTO Layer
 *
 * Represents a quantity consisting of:
 * - numeric value
 * - measurement unit
 *
 * Used internally between controller and service layers.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuantityDTO<U extends IMeasureable> {

    private double value;
    private U unit;

}