package com.siit.hospital_manager.service;


import com.siit.hospital_manager.exception.BusinessException;
import com.siit.hospital_manager.repository.SpecialisationsRepository;
import com.siit.hospital_manager.model.Specialisation;
import com.siit.hospital_manager.model.dto.CreateSpecialisationDto;
import com.siit.hospital_manager.model.dto.SpecialisationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialisationService {

    private final SpecialisationsRepository specialisationRepository;

    public List<SpecialisationDto> findAll() {
        return specialisationRepository
                .findAll()
                .stream()
                .map(SpecialisationDto::new)
                .toList();
    }

    public List<SpecialisationDto> findAllActive() {
        return specialisationRepository
                .findAllByIsActive(true)
                .stream()
                .map(SpecialisationDto::new)
                .toList();
    }

    public Integer createSpecialisation(CreateSpecialisationDto createSpecialisationDto) {
        specialisationRepository
                .findByName(createSpecialisationDto.getName())
                .ifPresent(s -> {throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Specialisation with the name " + createSpecialisationDto.getName() + " is already present");
                });
        Specialisation specialisation = Specialisation.builder()
                .name(createSpecialisationDto.getName())
                .isActive(true)
                .build();
        return specialisationRepository.save(specialisation).getId();
    }

    public Specialisation findSpecialisationById(Integer id) {
        return specialisationRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Specialisation with Id " + id + " not found"));
    }

    public Specialisation updateSpecialisation(CreateSpecialisationDto createSpecialisationDto, Integer id) {
        Specialisation specialisation = specialisationRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Specialisation with Id " + id + " not found"));
        specialisationRepository
                .findByName(createSpecialisationDto.getName())
                .ifPresent(s -> {throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Specialisation with the name " + createSpecialisationDto.getName() + " is already present");
                });
        if (createSpecialisationDto.getName() != null) {
            specialisation.setName(createSpecialisationDto.getName());
        }
        if (createSpecialisationDto.getIsActive() == null) {
            specialisation.setActive(true);
        } else specialisation.setActive(!createSpecialisationDto.getIsActive());
        specialisationRepository.save(specialisation);
        return specialisation;
    }

    public void deleteSpecialisationById(Integer id) {
        Specialisation specialisation = specialisationRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Specialisation with Id " + id + " not found"));
        specialisationRepository.delete(specialisation);
    }

}