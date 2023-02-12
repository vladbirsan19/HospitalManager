package com.siit.hospital_manager.repository;

import com.siit.hospital_manager.model.Appointment;
import com.siit.hospital_manager.model.Doctor;
import com.siit.hospital_manager.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findAllByPatientId(Integer id);
    List<Appointment> findAllByDoctorId(Integer id);
    List<Appointment> findAllByPatientIdAndDoctorIsActive(Integer id, boolean isActive);
    List<Appointment> findAllByDoctorIdAndPatientIsActive(Integer id, boolean isActive);
    Optional<Appointment> findByDate(LocalDateTime date);
    Optional<Appointment> findAppointmentByIdAndDoctor(Integer id, Doctor doctor);
    Optional<Appointment> findAppointmentByIdAndPatient(Integer id, Patient patient);
    @Modifying
    @Query(value = "DELETE FROM appointments where id = :id", nativeQuery = true)
    void deleteByIdNativeQuery(@Param("id") Integer id);





}