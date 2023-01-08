package com.SIIT.HospitalManager.controller;

import com.SIIT.HospitalManager.model.Appointment;
import com.SIIT.HospitalManager.model.dto.AppointmentDto;
import com.SIIT.HospitalManager.model.dto.CreateAppointmentDto;
import com.SIIT.HospitalManager.model.dto.UpdateAppointmentDto;
import com.SIIT.HospitalManager.repository.AppointmentsRepository;
import com.SIIT.HospitalManager.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentsController {

    private final AppointmentService appointmentService;



    @GetMapping("/patient/{id}")
    public List<AppointmentDto> getByPatientId(@PathVariable("id") Integer id) {
        return appointmentService.findAllByPatientId(id);
    }

    @GetMapping
    public List<AppointmentDto> findAll(){
        return appointmentService.findAll();
    }

    // CREATE, UPDATE

    @PostMapping
    public void createAppointment(@RequestBody @Valid CreateAppointmentDto createAppointmentDto){
        appointmentService.createAppointment(createAppointmentDto);
    }

    @PatchMapping
    public void updateAppointment(@RequestBody @Valid UpdateAppointmentDto updateAppointmentDto){
        appointmentService.updateAppointment(updateAppointmentDto);
    }


}