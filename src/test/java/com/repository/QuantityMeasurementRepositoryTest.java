package com.repository;



import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.quantitymeasurement.enums.OperationType;
import com.quantitymeasurement.repository.IQuantityMeasurementRepository;

/*
 * =========================================================
 * UC17 Repository Layer Test
 * =========================================================
 *
 * Purpose:
 * Verify database persistence using H2 in-memory DB.
 */


@DataJpaTest
class QuantityMeasurementRepositoryTest {

    private static final Logger logger =
            Logger.getLogger(QuantityMeasurementRepositoryTest.class.getName());

    @Autowired
    private IQuantityMeasurementRepository repository;

    @BeforeEach
    void setup() {
        logger.info("========== Repository Test Setup ==========");
        logger.info("H2 database initialized");
    }

    /*
     * =========================================================
     * Test: Save Operation
     * =========================================================
     */

    @Test
    void testSaveMeasurement() {

        logger.info("=========== START TEST: Save Entity ===========");

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        "Quantity(1,FEET)",
                        "Quantity(12,INCHES)",
                        OperationType.ADD,
                        "Quantity(2,FEET)"
                );

        logger.info("STEP 1: Saving entity to repository");

        QuantityMeasurementEntity saved =
                repository.save(entity);

        logger.info("STEP 2: Validating entity ID");

        assertNotNull(saved.getId());

        logger.info("RESULT: Entity saved successfully in H2 database");
        logger.info("=========== TEST PASSED ===========");
    }
}
