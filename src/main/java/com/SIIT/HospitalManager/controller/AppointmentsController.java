package com.SIIT.HospitalManager.controller;

import com.SIIT.HospitalManager.model.Appointment;
import com.SIIT.HospitalManager.repository.AppointmentsRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/appointments")
public class AppointmentsController {

    public AppointmentsController(AppointmentsRepository appointmentsRepository) {
        this.appointmentsRepository = appointmentsRepository;
    }

    private final AppointmentsRepository appointmentsRepository;

    @GetMapping("/patient/{id}")
    public List<Appointment> getByPatientId(@PathVariable("id") Integer id) {
        return appointmentsRepository.findAllByPatientId(id);
    }

}
