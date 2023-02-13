package com.siit.hospital_manager.controller;

import com.siit.hospital_manager.model.dto.AppointmentDto;
import com.siit.hospital_manager.model.dto.CreateAppointmentDto;
import com.siit.hospital_manager.service.AppointmentService;
import com.siit.hospital_manager.service.EmailSender;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

import static com.siit.hospital_manager.util.AuthUtils.*;
import static com.siit.hospital_manager.util.AuthUtils.isDoctor;


@Controller
@RequestMapping("/appointment")

@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final EmailSender emailSender;

    @GetMapping("/findAllByUserName")
    public String findAllByUserName(Model model, Principal principal, Authentication authentication) {
        if (isPatient(authentication)) {
            List<AppointmentDto> appointmentsList = appointmentService.findAllByPatientUserNameAndActiveDoctor(principal.getName());
            model.addAttribute("appointments", appointmentsList);
            return "appointment/viewAll";
        } else if (isDoctor(authentication)) {
            List<AppointmentDto> appointmentsList = appointmentService.findAllByDoctorUserName(principal.getName());
            model.addAttribute("appointments", appointmentsList);
            return "doctor/viewAllAppointments";
        } else {
            List<AppointmentDto> appointmentsList = appointmentService.findAll();
            model.addAttribute("appointments", appointmentsList);
            return "admin/viewAllAppointments";
        }
    }

    @GetMapping("/findAllByPatientId/{Id}")
    public String findAllByPatientId(Model model, @PathVariable("Id") Integer id, Authentication authentication) {
        if (isAdmin(authentication)) {
            List<AppointmentDto> appointmentsList = appointmentService.findAllByPatientId(id);
            model.addAttribute("appointments", appointmentsList);
            return "admin/viewPatientAppointments";
        }
        return "index";
    }

    @GetMapping("/findAllByDoctorId/{Id}")
    public String findAllByDoctorId(Model model, @PathVariable("Id") Integer id, Authentication authentication) {
        if (isAdmin(authentication)) {
            List<AppointmentDto> appointmentsList = appointmentService.findAllByDoctorId(id);
            model.addAttribute("appointments", appointmentsList);
            return "admin/viewDoctorAppointments";
        }
        return "index";
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteAppointmentById(@PathVariable Integer id, Principal principal, Authentication authentication) {
        if (isPatient(authentication)) {
            appointmentService.deleteAppointmentByIdAndPatient(id, principal.getName());
        } else if (isDoctor(authentication)) {
            appointmentService.deleteAppointmentByIdAndDoctor(id, principal.getName());
        } else appointmentService.deleteAppointmentById(id);
    }

    @GetMapping("/create/{doctorId}")
    public String createAppointment(@PathVariable("doctorId") Integer id, @ModelAttribute("createAppointmentDto") CreateAppointmentDto createAppointmentDto, Principal principal) {
        appointmentService.addDoctorToCreateAppointmentDto(createAppointmentDto, id);
        appointmentService.addPatientToCreateAppointmentDto(createAppointmentDto, principal.getName());
        return "appointment/create";
    }

    @PostMapping("/submit/{doctorId}")
    public String submitAppointmentForm(@Valid CreateAppointmentDto createAppointmentDto, BindingResult bindingResult, @PathVariable("doctorId") Integer id, Principal principal, Model model) {
        appointmentService.addDoctorToCreateAppointmentDto(createAppointmentDto, id);
        appointmentService.addPatientToCreateAppointmentDto(createAppointmentDto, principal.getName());
        model.addAttribute("createAppointmentDto", createAppointmentDto);
        if (bindingResult.hasErrors()) {
            return "appointment/create";
        }
        try {
            appointmentService.createAppointment(createAppointmentDto);
            String to = createAppointmentDto.getPatient().getEmail();
            String subject = "Appointment Confirmation";
            String body = "Your appointment has been confirmed. We are waiting for you on;" + createAppointmentDto.getDate();
            emailSender.sendAppointmentConfirmationEmail(to, subject, body);
        } catch (ResponseStatusException exception) {
            return "/entityExistsError";
        }
        return "redirect:/appointment/findAllByUserName";
    }

}
