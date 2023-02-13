package com.siit.hospital_manager.controller;


import com.siit.hospital_manager.model.dto.CreatePatientDto;
import com.siit.hospital_manager.model.dto.PatientDto;
import com.siit.hospital_manager.model.dto.UpdatePatientDto;
import com.siit.hospital_manager.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.siit.hospital_manager.util.AuthUtils.isAdmin;


@Controller
@RequestMapping("/mvc/patient")
@RequiredArgsConstructor
public class PatientMvcController {

    private final PatientService patientService;
    @GetMapping("viewAll")
    public String getAllPatients(Model model, Authentication authentication) {
        if (isAdmin(authentication)) {
            List<PatientDto> patientsList = patientService.findAll();
            model.addAttribute("patients", patientsList);
            return "admin/viewAllPatients";
        } else {
            List<PatientDto> activePatientsList = patientService.findAllActive();
            model.addAttribute("patients", activePatientsList);
            return "patient/viewAll";
        }
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        CreatePatientDto createPatientDto = new CreatePatientDto();

        model.addAttribute("createPatientDto", createPatientDto);
        return "patient/createPatient";
    }

    @PostMapping("/submitCreatePatientForm")
    public String submitCreatePatientForm (@Valid CreatePatientDto createPatientDto, BindingResult bindingResult , RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            return "patient/createPatient";
        }
        try {
            patientService.createPatient(createPatientDto);
        }
        catch (ResponseStatusException exception){
            return "/entityExistsError";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Account created successfully");
        return "redirect:/dashboard";
    }

    @PutMapping(value = "/updatePatient/{Id}")
    public String updatePatient(@PathVariable("Id") Integer id, @Valid UpdatePatientDto updatePatientDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "patient/validationError";
        }
        patientService.updatePatient(updatePatientDto, id);
        return "redirect:/mvc/patient/viewAll";
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deletePatientById(@PathVariable Integer id) {
        patientService.deletePatientById(id);
    }

}

