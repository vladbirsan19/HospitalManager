package com.siit.hospital_manager.controller;


import com.siit.hospital_manager.model.dto.DoctorDto;
import com.siit.hospital_manager.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
;
import java.security.Principal;

import static com.siit.hospital_manager.util.AuthUtils.*;


@RequiredArgsConstructor
@Controller
public class HomePageController {

    private final DoctorService doctorService;

    @GetMapping("/")
    public String homePage(Model model){
        model.addAttribute("message", "Virtual Clinic v1");
        return "index";
    }

    @GetMapping("/dashboard")
    public String showAdminDashBoard(Model model, Authentication authentication, Principal principal){
        model.addAttribute("message", "Virtual Clinic v1");
        if (isAdmin(authentication)) return "dashboard/adminDashboard";
        if (isPatient(authentication)) return "dashboard/patientDashboard";
        if (isDoctor(authentication)) {
            DoctorDto doctor = doctorService.findByName(principal.getName());
            model.addAttribute("doctor", doctor);
            return "dashboard/doctorDashboard";
        }
        return "index";
    }

}
