package com.siit.hospital_manager.service;

import com.siit.hospital_manager.exception.BusinessException;
import com.siit.hospital_manager.model.Doctor;
import com.siit.hospital_manager.model.Specialisation;
import com.siit.hospital_manager.model.dto.CreateDoctorDto;
import com.siit.hospital_manager.model.dto.DoctorDto;
import com.siit.hospital_manager.model.dto.UpdateDoctorDto;
import com.siit.hospital_manager.repository.DoctorRepository;
import com.siit.hospital_manager.repository.SpecialisationsRepository;
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
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SpecialisationsRepository specialisationRepository;

    public List<DoctorDto> findAll() {
        return doctorRepository
                .findAll()
                .stream()
                .map(DoctorDto::new)
                .toList();
    }

    public DoctorDto findById(Integer id) {
        Doctor doctor = doctorRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Doctor with id " + id + " not found"));
        return new DoctorDto(doctor);
    }

    public DoctorDto findActiveById(Integer id) {
        Doctor doctor = doctorRepository
                .findByIdAndIsActive(id, true)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Doctor with id " + id + " not found"));
        return new DoctorDto(doctor);
    }

    public DoctorDto findByName(String userName) {
        Doctor doctor = doctorRepository
                .findByUserName(userName)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Doctor with UserName " + userName + " not found"));
        return new DoctorDto(doctor);
    }

    public List<DoctorDto> findAllBySpecialisation(Specialisation specialisation) {
        return doctorRepository.findAllBySpecialisation(specialisation)
                .stream()
                .map(DoctorDto::new).toList();
    }

    public List<DoctorDto> findAllActiveDoctorsBySpecialisation(Specialisation specialisation) {
        return doctorRepository.findAllBySpecialisationAndIsActive(specialisation, true)
                .stream()
                .map(DoctorDto::new).toList();
    }


    @Transactional
    public Integer createDoctor(CreateDoctorDto createDoctorDto) {

        userRepository
                .findByUserName(createDoctorDto.getUserName())
                .ifPresent(user -> {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Doctor already exists!");
                        }
                );
        Doctor doctor = Doctor.builder()
                .userName(createDoctorDto.getUserName())
                .password(passwordEncoder.encode(createDoctorDto.getPassword()))
                .name(createDoctorDto.getName())
                .specialisation(createDoctorDto.getSpecialisation())
                .isActive(true)
                .roles("ROLE_DOCTOR")
                .build();
        return userRepository.save(doctor).getId();
    }

    public List<Specialisation> findAllSpecialisations() {
        return doctorRepository.findAll()
                .stream()
                .map(Doctor::getSpecialisation).toList();
    }

    public void deleteDoctorById(Integer id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "Invalid doctor id"));
        doctorRepository.delete(doctor);
    }

    public CreateDoctorDto addSpecialisationToCreateDoctorDto(CreateDoctorDto createDoctorDto, Integer specialisationId) {
        Specialisation specialisation = specialisationRepository
                .findById(specialisationId)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Specialisation with Id " + specialisationId + " not found"));
        createDoctorDto.setSpecialisation(specialisation);
        return createDoctorDto;
    }

    public Doctor updateDoctor(Integer id, UpdateDoctorDto updateDoctorDto) {
        Doctor doctor = doctorRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Doctor with Id " + id + " not found"));
        Specialisation specialisation = specialisationRepository
                .findByName(updateDoctorDto.getSpecialisation().getName())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Specialisation with name " + updateDoctorDto.getSpecialisation().getName() + " not found"));
        if (updateDoctorDto.getName() != null) {
            doctor.setName(updateDoctorDto.getName());
        }
        if (updateDoctorDto.getSpecialisation().getName() != null) {
            doctor.setSpecialisation(specialisation);
        }
        if (updateDoctorDto.getIsActive() == null) {
            doctor.setActive(true);
        } else doctor.setActive(!updateDoctorDto.getIsActive());
        doctorRepository.save(doctor);
        return doctor;
    }

}