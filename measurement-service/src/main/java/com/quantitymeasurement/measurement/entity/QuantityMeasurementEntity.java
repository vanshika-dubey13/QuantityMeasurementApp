package com.quantitymeasurement.measurement.entity;

import com.quantitymeasurement.measurement.enums.OperationType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * =========================================================
 * QuantityMeasurementEntity
 * =========================================================
 *
 * UC17 – JPA Entity
 */

@Entity
@Table(name = "quantity_measurements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuantityMeasurementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String operand1;

    private String operand2;

    @Enumerated(EnumType.STRING)
    private OperationType operation;

    private String result;

    private boolean error;

    /*
     * Constructor used by Service Layer
     */
    public QuantityMeasurementEntity( String operand1, String operand2, OperationType operation, String result) {

        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operation = operation;
        this.result = result;
        this.error = false;
    }


}

