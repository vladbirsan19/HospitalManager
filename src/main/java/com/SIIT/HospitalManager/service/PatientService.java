package com.SIIT.HospitalManager.service;

import com.SIIT.HospitalManager.exception.BusinessException;
import com.SIIT.HospitalManager.model.Patient;
import com.SIIT.HospitalManager.model.dto.CreatePatientDto;
import com.SIIT.HospitalManager.model.dto.PatientDto;
import com.SIIT.HospitalManager.model.dto.UpdatePatientDto;
import com.SIIT.HospitalManager.repository.PatientJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class PatientService {
    private final PatientJpaRepository patientJpaRepository;

    public PatientService(PatientJpaRepository patientJpaRepository) {
        this.patientJpaRepository = patientJpaRepository;
    }

    public List<PatientDto> findAll() {
        return patientJpaRepository.findAll()
                .stream()
                .map(PatientDto::new).toList();
    }

    public PatientDto findById(Integer id) {
        Patient patient = patientJpaRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Patient with id " + id + " not found"));
        return new PatientDto(patient);
    }

    public void createPatient(CreatePatientDto createPatientDto) {
        patientJpaRepository.save(new Patient(createPatientDto));
    }

    public void updatePatient(UpdatePatientDto updatePatientDto) {
        Patient patient = patientJpaRepository
                .findById(updatePatientDto.getId())
                .orElseThrow(() -> new BusinessException(HttpStatus.BAD_REQUEST, "Patient with id " + updatePatientDto.getId() + " not found"));

        if (updatePatientDto.getAge() != null) {
            patient.setAge(updatePatientDto.getAge());
        }
        patientJpaRepository.save(patient);
    }

    // same for delete
}


