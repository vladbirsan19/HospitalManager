package com.SIIT.HospitalManager.repository;

import com.SIIT.HospitalManager.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findAllByPatientId(Integer id);

}