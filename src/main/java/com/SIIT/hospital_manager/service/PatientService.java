package com.siit.hospital_manager.service;

import com.siit.hospital_manager.exception.BusinessException;
import com.siit.hospital_manager.model.Patient;
import com.siit.hospital_manager.model.dto.CreatePatientDto;
import com.siit.hospital_manager.model.dto.PatientDto;
import com.siit.hospital_manager.model.dto.UpdatePatientDto;
import com.siit.hospital_manager.repository.PatientRepository;
import com.siit.hospital_manager.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


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

    public Patient findByUserName(String userName) {
        return patientRepository.findByUserName(userName).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "Invalid patient id"));
    }

    @Transactional
    public Integer createPatient(CreatePatientDto createPatientDto) {

        userRepository.findByUserName(createPatientDto.getUserName()).ifPresent(user -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Patient already exists!");
                }
        );

        Patient patient = Patient.builder()
                .userName(createPatientDto.getUserName())
                .password(passwordEncoder.encode(createPatientDto.getPassword()))
                .firstName(createPatientDto.getFirstName())
                .lastName(createPatientDto.getLastName())
                .age(createPatientDto.getAge())
                .isActive(true)
                .roles("ROLE_PATIENT")
                .phoneNumber(createPatientDto.getPhoneNumber())
                .email(createPatientDto.getEmail())
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

    public void deletePatientById(Integer id) {
        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "Invalid doctor id"));
        patientRepository.delete(patient);
    }
}
