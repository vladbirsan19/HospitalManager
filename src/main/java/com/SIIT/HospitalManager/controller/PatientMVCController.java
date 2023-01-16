package com.SIIT.HospitalManager.controller;

import com.SIIT.HospitalManager.model.dto.CreatePatientDto;
import com.SIIT.HospitalManager.model.dto.PatientDto;
import com.SIIT.HospitalManager.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@Controller
@RequestMapping("/mvc/patient")
public class PatientMVCController {

    private final PatientService patientService;
    @GetMapping("/viewAll")
    public String getAllPatients(Model model) {
        List<PatientDto> patients = patientService.findAll();
        model.addAttribute("patients", patients);
        return "patient/viewAll";
    }

    @GetMapping("/create")
    public String showCreatePatientForm(Model model) {
        CreatePatientDto createPatientDto = new CreatePatientDto();
        model.addAttribute("createPatientDto", createPatientDto);
        return "patient/create";
    }

    @PostMapping("/submit")
    public String createPatient(@Valid CreatePatientDto createPatientDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "validationError";
        }
        model.addAttribute("createPatientDto", createPatientDto);
        patientService.createPatient(createPatientDto);
        return "success";
    }
}