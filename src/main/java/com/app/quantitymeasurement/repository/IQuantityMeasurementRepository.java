package com.app.quantitymeasurement.repository;


import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.enums.OperationType;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * =========================================================
 * QuantityMeasurementRepository
 * =========================================================
 *
 * UC17 – Repository Layer (Spring Data JPA)
 *
 * Purpose:
 * Handles database operations for QuantityMeasurementEntity.
 *
 * Spring Data JPA automatically provides implementations
 * for CRUD operations such as:
 *
 * save()
 * findById()
 * findAll()
 * delete()
 */

@Repository
public interface IQuantityMeasurementRepository  extends JpaRepository<QuantityMeasurementEntity, Long> {

	List<QuantityMeasurementEntity> findByOperation(OperationType operation);

	long countByOperationAndErrorFalse(OperationType operation);
}