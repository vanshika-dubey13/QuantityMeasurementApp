package com.quantitymeasurement.measurement.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quantitymeasurement.measurement.entity.QuantityMeasurementEntity;
import com.quantitymeasurement.measurement.enums.OperationType;

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