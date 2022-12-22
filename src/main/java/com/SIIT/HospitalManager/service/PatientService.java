package com.SIIT.HospitalManager.service;

import com.SIIT.HospitalManager.model.Patient;
import com.SIIT.HospitalManager.model.dto.CreatePatientDto;
import com.SIIT.HospitalManager.model.dto.PatientDto;
import com.SIIT.HospitalManager.model.dto.UpdatePatientDto;
import com.SIIT.HospitalManager.repository.PatientJdbcRepository;
import com.SIIT.HospitalManager.repository.PatientJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

//    private final PatientJdbcRepository patientJdbcRepository;
//
//    public PatientService(PatientJdbcRepository patientJdbcRepository) {
//        this.patientJdbcRepository = patientJdbcRepository;
//    }

    private final PatientJpaRepository patientJpaRepository;

    public PatientService(PatientJpaRepository patientJpaRepository) {
        this.patientJpaRepository = patientJpaRepository;
    }

//    public List<Patient> findAll() {
////        return patientJdbcRepository.findAll();
//
//    }

    public List<PatientDto> findAll() {
        return patientJpaRepository.findAll()
                .stream()
                .map(PatientDto::new).toList();
    }

    public PatientDto findById(Integer id) {
        Patient patient = patientJpaRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Patient with id " + id + " not found"));
        return new PatientDto(patient);
    }

    public void createPatient(CreatePatientDto createPatientDto) {
        patientJpaRepository.save(new Patient(createPatientDto));
    }

    public void updatePatient(UpdatePatientDto updatePatientDto) {
        Patient patient = patientJpaRepository
                .findById(updatePatientDto.getId())
                .orElseThrow(() -> new RuntimeException("Patient with id " + updatePatientDto.getId() + " not found"));
        patient.setAge(updatePatientDto.getAge());
        patientJpaRepository.save(patient);

    }
}




