package com.siit.hospital_manager.controller;


import com.siit.hospital_manager.model.dto.*;
import com.siit.hospital_manager.service.DoctorService;
import com.siit.hospital_manager.service.SpecialisationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

import static com.siit.hospital_manager.util.AuthUtils.isAdmin;
import static com.siit.hospital_manager.util.AuthUtils.isPatient;


@Controller
@RequiredArgsConstructor
@RequestMapping("/mvc/doctor")
public class DoctorController {

    private final DoctorService doctorService;
    private final SpecialisationService specialisationService;

    @GetMapping("/viewAll")
    public String viewAll(Model model, Authentication authentication){
        List<DoctorDto> doctorsList = doctorService.findAll();
        model.addAttribute("doctors", doctorsList);
        if (isAdmin(authentication)) {
            List<SpecialisationDto> specialisationList = specialisationService.findAll();
            model.addAttribute("specialisations", specialisationList);
            return "admin/viewAllDoctors";
        }
        else {
            return "doctor/viewAll";
        }
    }

    @GetMapping("/viewDoctorsBySpecialisation/{specialisationId}")
    public String viewDoctorsBySpecialisation(Model model, @PathVariable("specialisationId") Integer specialisationId, Authentication authentication) {
        List<DoctorDto> doctorsList = doctorService.findAllBySpecialisation(specialisationService.findSpecialisationById(specialisationId));
        if (doctorsList.isEmpty() && isAdmin(authentication)) {
            return "redirect:/mvc/doctor/createDoctor/{specialisationId}";
        }
        else if (isAdmin(authentication)) {
            model.addAttribute("doctors", doctorsList);
            return "admin/viewDoctorsBySpecialisation";
        }
        else {
            List<DoctorDto> activeDoctorsList = doctorService.findAllActiveDoctorsBySpecialisation(specialisationService.findSpecialisationById(specialisationId));
            model.addAttribute("doctors", activeDoctorsList);
            return "doctor/viewDoctorsBySpecialisation";
        }
    }

    @GetMapping("/viewDoctorProfile/{id}")
    public String viewDoctorProfile(Model model, @PathVariable("id") Integer id, Authentication authentication) {
        if (isAdmin(authentication)) {
            DoctorDto doctorDto = doctorService.findById(id);
            model.addAttribute("doctor", doctorDto);
            List<SpecialisationDto> specialisationList = specialisationService.findAll();
            model.addAttribute("specialisations", specialisationList);
            return "admin/viewDoctorProfile";
        } else {
            DoctorDto doctor = doctorService.findActiveById(id);
            model.addAttribute("doctor", doctor);
            if (isPatient(authentication)) return "patient/viewDoctorProfile";
            else return "doctor/viewDoctorProfile";
        }
    }

    @GetMapping("/createDoctor/{specialisationId}")
    public String showCreateForm(@PathVariable("specialisationId") Integer specialisationId, CreateDoctorDto createDoctorDto, Model model) {
        doctorService.addSpecialisationToCreateDoctorDto(createDoctorDto, specialisationId);
        model.addAttribute("createDoctorDto", createDoctorDto);
        return "doctor/createDoctor";
    }

    @PostMapping(value = "/submitCreateDoctorForm/{specialisationId}")
    @Operation(summary = "This is for creating doctors", description = "This is the long description",
            responses = {@ApiResponse(responseCode = "201", description = "All went well"),
                    @ApiResponse(responseCode = "400", description = "Some fields are invalid or missing")}
    )
    public String createDoctor(@Valid CreateDoctorDto createDoctorDto, @PathVariable("specialisationId") Integer specialisationId, BindingResult bindingResult, Model model){
        doctorService.addSpecialisationToCreateDoctorDto(createDoctorDto, specialisationId);
        if (bindingResult.hasErrors()) {
            return "doctor/createDoctor";
        }
        try {
            doctorService.createDoctor(createDoctorDto);
        } catch (ResponseStatusException exception) {
            return "/entityExistsError";
        }
        model.addAttribute("doctorDto", createDoctorDto);
        return "doctor/successDoctorCreation";
    }

    @PutMapping(value = "/updateDoctor/{Id}")
    public String updateDoctor(@PathVariable("Id") Integer id, @Valid UpdateDoctorDto updateDoctorDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "doctor/validationError";
        }
        doctorService.updateDoctor(id, updateDoctorDto);
        return "redirect:/mvc/doctor/viewAll";
    }

    @PutMapping(value = "/docProfile/updateDoctor/{Id}")
    public String changeNameDocProfile(@PathVariable("Id") Integer id, @Valid UpdateDoctorDto updateDoctorDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "doctor/validationError";
        }
        doctorService.updateDoctor(id, updateDoctorDto);
        return "redirect:/mvc/doctor/viewDoctorProfile/{Id}";
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteDoctorById(@PathVariable Integer id){
        doctorService.deleteDoctorById(id);
    }
}
