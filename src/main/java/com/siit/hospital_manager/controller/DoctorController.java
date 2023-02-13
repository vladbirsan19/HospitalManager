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
//    private final AppointmentService appointmentService;

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
        model.addAttribute("doctors", doctorsList);
        if (doctorsList.isEmpty() && isAdmin(authentication)) return "redirect:/mvc/doctor/createDoctor/{specialisationId}";
        else if (isAdmin(authentication)) return "admin/viewDoctorsBySpecialisation";
        else {
            return "doctor/viewDoctorsBySpecialisation";
        }
    }

    @GetMapping("/viewDoctorProfile/{id}")
    public String viewDoctorProfile(Model model, @PathVariable("id") Integer id, Authentication authentication) {
        DoctorDto doctor = doctorService.findById(id);
        model.addAttribute("doctor", doctor);
        if (isPatient(authentication)) return "patient/viewDoctorProfile";
        else if (isAdmin(authentication)) {
            List<SpecialisationDto> specialisationList = specialisationService.findAll();
            model.addAttribute("specialisations", specialisationList);
            return "admin/viewDoctorProfile";
        }
        else return "doctor/viewDoctorProfile";
    }

    @GetMapping("/createDoctor/{specialisationId}")
    public String showCreateForm(@PathVariable("specialisationId") Integer specialisationId, @ModelAttribute("doctorDto") CreateDoctorDto createDoctorDto) {
        doctorService.addSpecialisationToCreateDoctorDto(createDoctorDto, specialisationId);
        return "doctor/createDoctor";
    }

    @PostMapping(value = "/submitCreateDoctorForm/{specialisationId}")
    @Operation(summary = "This is for creating doctors", description = "This is the long description",
            responses = {@ApiResponse(responseCode = "201", description = "All went well"),
                    @ApiResponse(responseCode = "400", description = "Some fields are invalid or missing")}
    )
    public String createDoctor(@Valid CreateDoctorDto createDoctorDto, @PathVariable("specialisationId") Integer specialisationId, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()) {
            return "doctor/validationError";
        }
        doctorService.addSpecialisationToCreateDoctorDto(createDoctorDto, specialisationId);
        try {
            doctorService.createDoctor(createDoctorDto);
        } catch (ResponseStatusException exception) {
            return "/entityExistsError";
        }
        model.addAttribute("doctorDto", createDoctorDto);
        return "doctor/successDoctorCreation";
    }

    @PostMapping(value = "/changeName/{Id}")
    public String changeName(@PathVariable("Id") Integer id, @Valid @ModelAttribute("doctor") UpdateDoctorDto updateDoctorDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "doctor/validationError";
        }
        doctorService.changeName(updateDoctorDto, id);
        return "redirect:/mvc/doctor/viewAll";
    }

    @PostMapping(value = "/changeSpecialisation/{Id}")
    public String changeSpecialisation(@PathVariable("Id") Integer id, @Valid @ModelAttribute("doctor") CreateSpecialisationDto createSpecialisationDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "doctor/validationError";
        }
        doctorService.changeSpecialisationName(createSpecialisationDto, id);
        return "redirect:/mvc/doctor/viewAll";
    }

    @PostMapping(value = "/docProfile/changeName/{Id}")
    public String changeNameDocProfile(@PathVariable("Id") Integer id, @Valid @ModelAttribute("doctor") UpdateDoctorDto updateDoctorDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "doctor/validationError";
        }
        doctorService.changeName(updateDoctorDto, id);
        return "redirect:/mvc/doctor/viewDoctorProfile/{Id}";
    }

    @PostMapping(value = "/docProfile/changeSpecialisation/{Id}")
    public String changeSpecialisationDocProfile(@PathVariable("Id") Integer id, @Valid @ModelAttribute("doctor") CreateSpecialisationDto createSpecialisationDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "doctor/validationError";
        }
        doctorService.changeSpecialisationName(createSpecialisationDto, id);
        return "redirect:/mvc/doctor/viewDoctorProfile/{Id}";
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteDoctorById(@PathVariable Integer id){
        doctorService.deleteDoctorById(id);
//        appointmentService.deleteAppointmentByDoctorId(id);
    }
}
