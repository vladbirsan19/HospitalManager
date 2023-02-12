package com.siit.hospital_manager.controller;

import com.siit.hospital_manager.model.Specialisation;
import com.siit.hospital_manager.model.dto.CreateSpecialisationDto;
import com.siit.hospital_manager.model.dto.SpecialisationDto;
import com.siit.hospital_manager.service.DoctorService;
import com.siit.hospital_manager.service.SpecialisationService;
import com.siit.hospital_manager.util.AuthUtils;
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


@Controller
@RequestMapping("/specialisation")
@RequiredArgsConstructor
public class SpecialisationController {

    private final SpecialisationService specialisationService;
    private final DoctorService doctorService;

    @GetMapping("/viewAll")
    public String findAllSpecialisations(Model model, Authentication authentication) {
        if (isAdmin(authentication)) {
            List<SpecialisationDto> specialisationList = specialisationService.findAll();
            model.addAttribute("specialisations", specialisationList);
            return "admin/viewAllSpecialisations";
        } else {
            List<SpecialisationDto> specialisationList = specialisationService.findAllActive();
            model.addAttribute("specialisations", specialisationList);
            return "specialisation/viewAll";
        }
    }

    @GetMapping("/create")
    public String showCreateSpecialisationForm(Model model) {
        CreateSpecialisationDto createSpecialisationDto = new CreateSpecialisationDto();

        model.addAttribute("specialisation", createSpecialisationDto);
        return "specialisation/create";
    }

    @PostMapping(value = "/submit")
    public String createSpecialisation(@ModelAttribute("specialisation") CreateSpecialisationDto createSpecialisationDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "specialisation/validationError";
        }
        try {
            specialisationService.createSpecialisation(createSpecialisationDto);
        } catch (ResponseStatusException exception) {
            return "/entityExistsError";
        }
        return "redirect:viewAll";
    }

    @GetMapping("/viewDoctorSpecialisations")
    public String viewDoctorSpecialisations(Model model) {
        List<Specialisation> specialisationList = doctorService.findAllSpecialisations();
        model.addAttribute("specialisations", specialisationList);

        return "doctor/viewSpecialisations";
    }

    @PutMapping("/{Id}")
    public String updateSpecialisation(@PathVariable("Id") Integer id, @Valid @ModelAttribute("specialisation") CreateSpecialisationDto createSpecialisationDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "specialisation/validationError";
        }
        specialisationService.updateSpecialisation(createSpecialisationDto, id);
        return "redirect:/specialisation/viewAll";
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteSpecialisationById(@PathVariable Integer id){
        specialisationService.deleteSpecialisationById(id);
    }

}
