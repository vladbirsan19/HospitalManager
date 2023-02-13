package com.siit.hospital_manager.repository;


import com.siit.hospital_manager.model.Doctor;
import com.siit.hospital_manager.model.Specialisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    Optional<Doctor> findByUserName(String name);

    List<Doctor> findAllBySpecialisation(Specialisation specialisation);

    Optional<Doctor> findByName(String name);
}