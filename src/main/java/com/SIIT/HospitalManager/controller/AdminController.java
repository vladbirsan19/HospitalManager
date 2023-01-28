package com.SIIT.HospitalManager.controller;

import com.SIIT.HospitalManager.model.dto.CreateDoctorDto;
import com.SIIT.HospitalManager.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mvc/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/createDoctor")
    public String createDoctor(Model model){
        model.addAttribute("doctor", CreateDoctorDto.builder().build());
        return "admin/createDoctor";
    }

    @PostMapping("/submitCreateDoctorForm")
    @ResponseStatus(HttpStatus.CREATED)
    public String submitCreateDoctorForm(CreateDoctorDto createDoctorDto){
        adminService.createDoctor(createDoctorDto);
        return "redirect:/mvc/doctor/viewAll";
    }



}
