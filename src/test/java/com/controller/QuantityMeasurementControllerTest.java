package com.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quantitymeasurement.QuantityMeasurementApplication;
import com.quantitymeasurement.dto.QuantityInputDTO;
import com.quantitymeasurement.enums.OperationType;

/*
 * =========================================================
 * UC17 Controller Layer Test
 * =========================================================
 *
 * Purpose:
 * Validate REST API endpoints using MockMvc.
 *
 * Flow Tested:
 * Test -> HTTP Request -> Controller -> Service -> Response DTO
 */

@SpringBootTest(classes = QuantityMeasurementApplication.class)
@AutoConfigureMockMvc
class QuantityMeasurementControllerTest {

    private static final Logger logger =
            Logger.getLogger(QuantityMeasurementControllerTest.class.getName());

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        logger.info("========== Controller Test Setup ==========");
        logger.info("Initializing MockMvc and ObjectMapper");
    }

    /*
     * =========================================================
     * Test: Addition API
     * =========================================================
     *
     * Example:
     * 1 ft + 12 in = 2 ft
     */

    @Test
    void testAddAPI() throws Exception {

        logger.info("=========== START TEST: Add API ===========");

        QuantityInputDTO input = new QuantityInputDTO();

        input.setValue1(1);
        input.setUnit1("ft");

        input.setValue2(12);
        input.setUnit2("in");

        input.setOperation(OperationType.ADD);

        logger.info("STEP 1: Sending POST request to /api/v1/quantities/add");

        mockMvc.perform(
                post("/api/v1/quantities/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result").value(2.0));

        logger.info("RESULT: API returned correct addition result");
        logger.info("=========== TEST PASSED ===========");
    }

    /*
     * =========================================================
     * Test: Conversion API
     * =========================================================
     */

    @Test
    void testConvertAPI() throws Exception {

        logger.info("=========== START TEST: Convert API ===========");

        QuantityInputDTO input = new QuantityInputDTO();

        input.setValue1(1);
        input.setUnit1("ft");
        input.setTargetUnit("in");

        logger.info("STEP 1: Sending POST request to /api/v1/quantities/convert");

        mockMvc.perform(
                post("/api/v1/quantities/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result").value(12.0));

        logger.info("RESULT: API returned correct conversion result");
        logger.info("=========== TEST PASSED ===========");
    }
}