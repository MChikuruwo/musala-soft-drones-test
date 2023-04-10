package com.musala.musalatest.business.dao;

import com.musala.musalatest.business.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicationRepository extends JpaRepository<Medication,Long> {

    Optional<Medication> findMedicationByCode(@Param("code") String code);

    Optional<Medication>findMedicationByName(@Param("name") String name);
}
