package com.quantitymeasurement.repository;


import com.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.quantitymeasurement.enums.OperationType;

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