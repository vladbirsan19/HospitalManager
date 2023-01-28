package com.SIIT.HospitalManager.service;

import com.SIIT.HospitalManager.exception.BusinessException;
import com.SIIT.HospitalManager.model.Patient;
import com.SIIT.HospitalManager.model.dto.CreatePatientDto;
import com.SIIT.HospitalManager.model.dto.PatientDto;
import com.SIIT.HospitalManager.model.dto.UpdatePatientDto;
import com.SIIT.HospitalManager.model.User;
import com.SIIT.HospitalManager.repository.PatientRepository;
import com.SIIT.HospitalManager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public List<PatientDto> findAll() {
        return patientRepository.findAll()
                .stream()
                .map(PatientDto::new).toList();
    }

    public PatientDto findById(Integer id) {
        Patient patient = patientRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Patient with id " + id + " not found"));
        return new PatientDto(patient);
    }


    public Integer createPatient(CreatePatientDto createPatientDto) {

        userRepository.findByUserName(createPatientDto.getUserName()).ifPresent(user -> {
                    throw new BusinessException(HttpStatus.BAD_REQUEST, "Patient already exists!");
                }
        );

        User patient = Patient.builder()
                .userName(createPatientDto.getUserName())
                .password(passwordEncoder.encode(createPatientDto.getPassword()))
                .name(createPatientDto.getName())
                .age(createPatientDto.getAge())
                .isActive(true)
                .roles("ROLE_PATIENT")
                .phoneNumber(createPatientDto.getPhoneNumber())
                .build();
        return userRepository.save(patient).getId();
    }

    public void updatePatient(UpdatePatientDto updatePatientDto) {
        Patient patient = patientRepository
                .findById(updatePatientDto.getId())
                .orElseThrow(() -> new BusinessException(HttpStatus.BAD_REQUEST, "Patient with id " + updatePatientDto.getId() + " not found"));

        if (updatePatientDto.getAge() != null) {
            patient.setAge(updatePatientDto.getAge());
        }
        patientRepository.save(patient);
    }

    // same for delete
}