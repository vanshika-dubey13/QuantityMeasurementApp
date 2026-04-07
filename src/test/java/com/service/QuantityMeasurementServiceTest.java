package com.service;



import static org.junit.jupiter.api.Assertions.*;

import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.quantitymeasurement.QuantityMeasurementApplication;
import com.quantitymeasurement.dto.QuantityInputDTO;
import com.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.quantitymeasurement.enums.OperationType;
import com.quantitymeasurement.service.IQuantityMeasurementService;

/*
 * =========================================================
 * UC17 Service Layer Test
 * =========================================================
 *
 * Purpose:
 * Validate business logic implemented in service layer.
 */

@SpringBootTest(classes = QuantityMeasurementApplication.class)
class QuantityMeasurementServiceTest {

    private static final Logger logger =
            Logger.getLogger(QuantityMeasurementServiceTest.class.getName());

    @Autowired
    private IQuantityMeasurementService service;

    @BeforeEach
    void setup() {
        logger.info("========== Service Test Setup ==========");
        logger.info("Service initialized from Spring context");
    }

    /*
     * =========================================================
     * Test: Length Addition
     * =========================================================
     */

    @Test
    void testAddition() {

        logger.info("=========== START TEST: Addition ===========");

        QuantityInputDTO input = new QuantityInputDTO();

        input.setValue1(1);
        input.setUnit1("ft");

        input.setValue2(12);
        input.setUnit2("in");

        logger.info("STEP 1: Calling service.add()");

        QuantityMeasurementDTO result = service.add(input);

        logger.info("STEP 2: Validating result");

        assertEquals(2.0, result.getResult());

        logger.info("RESULT: Addition successful");
        logger.info("=========== TEST PASSED ===========");
    }

    /*
     * =========================================================
     * Test: Conversion
     * =========================================================
     */

    @Test
    void testConversion() {

        logger.info("=========== START TEST: Conversion ===========");

        QuantityInputDTO input = new QuantityInputDTO();

        input.setValue1(1);
        input.setUnit1("ft");
        input.setTargetUnit("in");

        logger.info("STEP 1: Calling service.convert()");

        QuantityMeasurementDTO result = service.convert(input);

        assertEquals(12.0, result.getResult());

        logger.info("RESULT: Conversion successful");
        logger.info("=========== TEST PASSED ===========");
    }

    /*
     * =========================================================
     * Test: Temperature Arithmetic Restriction
     * =========================================================
     */

    @Test
    void testTemperatureAdditionBlocked() {

        logger.info("=========== START TEST: Temperature Restriction ===========");

        QuantityInputDTO input = new QuantityInputDTO();

        input.setValue1(30);
        input.setUnit1("C");

        input.setValue2(20);
        input.setUnit2("C");

        input.setOperation(OperationType.ADD);

        logger.info("STEP 1: Expecting exception");

        assertThrows(RuntimeException.class,
                () -> service.add(input));

        logger.info("RESULT: Temperature arithmetic correctly blocked");
        logger.info("=========== TEST PASSED ===========");
    }
}
