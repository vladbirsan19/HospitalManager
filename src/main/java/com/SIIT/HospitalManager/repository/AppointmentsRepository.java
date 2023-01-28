package com.SIIT.HospitalManager.repository;
import com.SIIT.HospitalManager.model.Appointment;
import com.SIIT.HospitalManager.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findAllByPatientId(Integer id);

    Optional<Appointment> findAppointmentByIdAndPatient(Integer id, Patient patient);

    @Modifying
    @Query(value = "DELETE FROM appointments where id = :id", nativeQuery = true)
    void deleteByIdNativeQuery(@Param("id") Integer id);
}