package com.SIIT.HospitalManager.service;

import com.SIIT.HospitalManager.exception.BusinessException;
import com.SIIT.HospitalManager.model.Doctor;
import com.SIIT.HospitalManager.model.dto.CreateDoctorDto;
import com.SIIT.HospitalManager.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class AdminService {

    private final DoctorRepository doctorRepository;
    public void createDoctor(CreateDoctorDto createDoctorDto) {
        Doctor doctor = Doctor.fromDto(createDoctorDto);

        doctorRepository.findByName(createDoctorDto.getName()).ifPresent(doctorInDb -> {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Doctor already exists!");
        });
        doctorRepository.save(doctor);
    }
}