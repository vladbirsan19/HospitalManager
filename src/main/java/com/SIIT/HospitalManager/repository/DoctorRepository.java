package com.SIIT.HospitalManager.repository;
import com.SIIT.HospitalManager.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    Optional<Doctor> findByName(String name);

}