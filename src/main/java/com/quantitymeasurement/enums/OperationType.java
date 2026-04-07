package com.quantitymeasurement.enums;


/*
 * =========================================================
 * OperationType Enum
 * =========================================================
 *
 * UC17 – Enum Layer
 *
 * Purpose:
 * Represents all quantity measurement operations
 * supported by the system.
 *
 * Using enum ensures:
 * - Type safety
 * - Consistency across layers
 * - Avoids magic strings like "ADD", "COMPARE"
 */

public enum OperationType {

    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE,
    COMPARE,
    CONVERT
}