package com.SIIT.HospitalManager.service;

import com.SIIT.HospitalManager.model.Doctor;
import com.SIIT.HospitalManager.model.dto.DoctorDto;
import com.SIIT.HospitalManager.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    public List<DoctorDto> findAll(){
        return doctorRepository
                .findAll()
                .stream()
                .map(Doctor::toDto)
                .toList();
    }

}