package com.SIIT.HospitalManager.repository;

import com.SIIT.HospitalManager.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientJpaRepository extends JpaRepository<Patient, Integer> {

}
