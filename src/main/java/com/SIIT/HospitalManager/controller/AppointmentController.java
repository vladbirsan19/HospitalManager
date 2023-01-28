package com.SIIT.HospitalManager.controller;
import com.SIIT.HospitalManager.model.dto.AppointmentDto;
import com.SIIT.HospitalManager.service.AppointmentService;
import com.SIIT.HospitalManager.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
@Controller
@RequestMapping("/appointment")

@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final DoctorService doctorService;

    @GetMapping("/findAllByPatient")
    public String findAllByPatient(Model model, Principal principal) {
        List<AppointmentDto> appointments = appointmentService.findAllByUserName(principal.getName());
        model.addAttribute("appointments", appointments);

        return "appointment/viewAll";
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteAppointmentById(Model model, @PathVariable Integer id, Principal principal){
        appointmentService.deleteAppointmentByIdAndPatient(id, principal.getName());
    }

    @GetMapping("/create")
    public String create(Model model, Principal principal) {
        model.addAttribute("doctors", doctorService.findAll());


        return "appointment/create";
    }

}
