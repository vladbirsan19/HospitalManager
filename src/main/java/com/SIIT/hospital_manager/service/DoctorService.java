package com.siit.hospital_manager.service;

import com.siit.hospital_manager.exception.BusinessException;
import com.siit.hospital_manager.model.Doctor;
import com.siit.hospital_manager.model.Specialisation;
import com.siit.hospital_manager.model.dto.CreateDoctorDto;
import com.siit.hospital_manager.model.dto.CreateSpecialisationDto;
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

    public void deleteDoctorById(Integer id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "Invalid doctor id"));
        doctorRepository.delete(doctor);
    }

    public List<Specialisation> findAllSpecialisations() {
        return doctorRepository.findAll()
                .stream()
                .map(Doctor::getSpecialisation).toList();
    }

    public CreateDoctorDto addSpecialisationToCreateDoctorDto(CreateDoctorDto createDoctorDto, Integer specialisationId) {
        Specialisation specialisation = specialisationRepository
                .findById(specialisationId)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Specialisation with Id " + specialisationId + " not found"));
        createDoctorDto.setSpecialisation(specialisation);
        return createDoctorDto;
    }

    public Doctor changeName(UpdateDoctorDto updateDoctorDto, Integer id) {
        Doctor doctor = doctorRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Doctor with Id " + id + " not found"));
        if (updateDoctorDto.getName() != null) {
            doctor.setName(updateDoctorDto.getName());
        }
        doctorRepository.save(doctor);
        return doctor;
    }

    public Doctor changeSpecialisationName(CreateSpecialisationDto createSpecialisationDto, Integer id) {
        Doctor doctor = doctorRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Doctor with Id " + id + " not found"));
        Specialisation specialisation = specialisationRepository
                .findByName(createSpecialisationDto.getName())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Specialisation with name " + createSpecialisationDto.getName() + " not found"));
        if (createSpecialisationDto.getName() != null) {
            doctor.setSpecialisation(specialisation);
        }
        doctorRepository.save(doctor);
        return doctor;
    }

//    public void deleteDoctorBySpecialisationId(Integer id) {
//        Specialisation specialisation = specialisationRepository
//                .findById(id)
//                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Specialisation with Id " + id + " not found"));
//        doctorRepository
//                .findAllBySpecialisation(specialisation)
//                .forEach(doctorRepository::delete);
//    }

}