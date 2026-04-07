package com.quantitymeasurement.measurement.dto;



import com.quantitymeasurement.measurement.enums.OperationType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * =========================================================
 * QuantityInputDTO
 * =========================================================
 *
 * UC17 – API Request DTO
 *
 * Represents the input received from REST API.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuantityInputDTO {

	@NotNull
    private double value1;
	@NotBlank
    private String unit1;

    @NotNull
    private double value2;
    @NotBlank
    private String unit2;

    private String targetUnit;

    @NotNull
    private OperationType operation;


}