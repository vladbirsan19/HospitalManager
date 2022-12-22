package com.SIIT.HospitalManager.controller;

import com.SIIT.HospitalManager.model.Patient;
import com.SIIT.HospitalManager.model.dto.CreatePatientDto;
import com.SIIT.HospitalManager.model.dto.PatientDto;
import com.SIIT.HospitalManager.model.dto.UpdatePatientDto;
import com.SIIT.HospitalManager.service.PatientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<PatientDto> findAll() {
        return patientService.findAll();
    }

    @PostMapping
    public void createPatient(@RequestBody CreatePatientDto createPatientDto) {
        patientService.createPatient(createPatientDto);
    }

    @GetMapping("{id}")
    public PatientDto findById(@PathVariable("id") Integer id) {
        return patientService.findById(id);
    }

    @PatchMapping

    public void updatePatient(@RequestBody UpdatePatientDto updatePatientDto){
        patientService.updatePatient(updatePatientDto);
    }
}



